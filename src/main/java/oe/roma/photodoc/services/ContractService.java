package oe.roma.photodoc.services;

import oe.roma.photodoc.domain.*;
import oe.roma.photodoc.domain.CustomerObject;
import oe.roma.photodoc.domain.File;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.sql.DataSource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.List;

/**
 * Created by us8610 on 19.06.14.
 */
@Transactional
@Service("contractService")
@SuppressWarnings("unchecked")
public class ContractService {

    private final String OBLDEVEL = "//Obl-devel/photodoc/";
    //private static final String obldevel = "//Obl-devel/photodoc/";
    private SimpleJdbcCall jdbcCall;
    private List<Procedure> recordsList;
    private JdbcTemplate jdbcTemplate;
    private static final int IMG_WIDTH = 1024;
    private static final int IMG_HEIGHT = 768;


    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                .withSchemaName("dbo")
                .withProcedureName("CallProcedures")
                .returningResultSet("records", procedureRowMapper);
    }

    public List<Procedure> recordsList(String contractNumber, String counterNumber, Integer rem_id) {

        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("ContractNumber", contractNumber);
        params.addValue("rem_id", rem_id);
        params.addValue("CounterNumber", counterNumber);
        Map<String, Object> results = jdbcCall.execute(params);
        setRecordsList((List) results.get("records"));
        return (List) results.get("records");
    }

    public Set<Customer> customerList(String contractNumber, String counterNumber, Integer rem_id, Date start_date, Date end_date) {

        Set<Customer> customerList = new HashSet<Customer>();
        for (Procedure procedure : recordsList(contractNumber, counterNumber, rem_id)) {
            customerList.add(new Customer(procedure.getContract_number(), procedure.getCustomer_name()));
        }
        for (Customer customer : customerList) {
            customer.setObjects(objectsList(customer, rem_id, start_date, end_date));
        }
        setCustomerFiles(rem_id, start_date, end_date, customerList);
        customerList = filterCustomersByDates(start_date, end_date, customerList);

        return customerList;
    }

    private void setCustomerFiles(Integer rem_id, Date start_date, Date end_date, Set<Customer> customerList) {
        for (Customer customer : customerList) {
            for (CustomerObject object : customer.getObjects()) {
                object.setFiles(getFilesByCountID(object.getCounter_id(), rem_id, start_date, end_date));
            }
        }
    }

    private Set<Customer> filterCustomersByDates(Date start_date, Date end_date, Set<Customer> customerList) {
        if (start_date != null || end_date != null) {
            Set<Customer> customerSet = new HashSet<Customer>();
            for (Customer temp_customer : customerList) {
                for (CustomerObject temp_object : temp_customer.getObjects()) {

                    if (temp_object.getFiles() != null) {
                        for (File temp_file : temp_object.getFiles()) {
                            Date date = temp_file.getDate();
                            if (isDateBetween(start_date, end_date, date)) {
                                customerSet.add(temp_customer);
                            } else if (isDateGreaterThan(start_date, date)) {
                                customerSet.add(temp_customer);
                            } else if (isDateLessThan(end_date, date)) {
                                customerSet.add(temp_customer);
                            }
                        }
                    }
                }
            }
            customerList = customerSet;
        }
        return customerList;
    }

    private boolean isDateLessThan(Date end_date, Date date) {
        return (end_date != null) && (date.equals(end_date) || date.before(end_date));
    }

    private boolean isDateGreaterThan(Date start_date, Date date) {
        return (start_date != null) && (date.equals(start_date) || date.after(start_date));
    }

    private boolean isDateBetween(Date start_date, Date end_date, Date date) {
        return (start_date != null && end_date != null)
                && (date.equals(start_date) || date.after(start_date))
                && (date.equals(end_date) || date.before(end_date));
    }


    public List<CustomerObject> objectsList(Customer customer, Integer rem_id, Date start_date, Date end_date) {

        List<CustomerObject> objectsList = new ArrayList<CustomerObject>();
        for (Procedure procedure : recordsList) {
            if (procedure.getCustomer_name().equals(customer.getName())) {
                objectsList.add(new CustomerObject(procedure.getObject_name(), procedure.getAddress(), procedure.getInspector(), procedure.getCounter_number(), procedure.getCounter_id()));

            }
        }

        for (CustomerObject tobject : objectsList) {
            tobject.setFiles(getFilesByCountID(tobject.getCounter_id(), rem_id, start_date, end_date));
        }

        if (start_date != null || end_date != null) {
            List<CustomerObject> list = new ArrayList<CustomerObject>();
            for (CustomerObject object : objectsList) {
                if (!object.getFiles().isEmpty()) {
                    list.add(object);
                }
            }
            objectsList = list;
        }

        return objectsList;
    }

    public List<File> getFilesByCountID(int count_id, int rem_id, Date start_date, Date end_date) {
        String sql = "SELECT f.[id] " +
                "      ,[counter_id]" +
                "      ,[value_type_id]" +
                "      ,[rem_id]" +
                "      ,[href]" +
                "      ,[img_name]" +
                "      ,[date]" +
                "      ,[type_id]" +
                "      ,[user_name]" +
                "      ,t.name AS [type_name]" +
                "      ,r.name AS [rem_name]" +
                "      ,t.document_type AS [document_type]" +
                "  FROM [files] f " +
                "  LEFT JOIN rem AS r ON f.[rem_id] = r.id " +
                "  LEFT JOIN type_documents AS t ON f.[type_id] = t.id " +
                "  where counter_id=? and rem_id=?";
        if (start_date != null) sql += " and date>='" + new java.sql.Date(start_date.getTime()) + "'";
        if (end_date != null) sql += " and date<='" + new java.sql.Date(end_date.getTime()) + "'";
        //System.err.println((jdbcTemplate.query(sql, new Object[]{count_id, rem_id}, filesRowMapper).toString()));
        return jdbcTemplate.query(sql, new Object[]{count_id, rem_id}, filesRowMapper);
    }


    public boolean delete(final int id, String filename) {
        if (deleteFile(filename)) {
            jdbcTemplate.update("DELETE FROM dbo.files where id=?", new PreparedStatementSetter() {
                public void setValues(PreparedStatement ps) throws SQLException {
                    ps.setInt(1, id);
                }
            });
            return true;
        }
        return false;
    }


    RowMapper<Procedure> procedureRowMapper = new RowMapper<Procedure>() {
        public Procedure mapRow(ResultSet rs, int rowNum) throws SQLException {
            Procedure customer = new Procedure();
            customer.setCustomer_name(rs.getString("ShortName"));
            customer.setContract_number(rs.getString("ContractNumber"));
            customer.setCounter_number(rs.getString("CounterNumber"));
            customer.setObject_name(rs.getString("Name"));
            customer.setAddress(rs.getString("FullAddress"));
            customer.setInspector(rs.getString("SegmentName"));
            customer.setCounter_id(rs.getInt("CounterId"));
            return customer;
        }
    };

    RowMapper<File> filesRowMapper = new RowMapper<File>() {
        public File mapRow(ResultSet rs, int rowNum) throws SQLException {
            File file = new File();
            file.setId(rs.getInt("id"));
            file.setCounter_id(rs.getInt("counter_id"));
            file.getDepartment().setId(rs.getInt("rem_id"));
            file.getDepartment().setName(rs.getString("rem_name"));
            file.setHref(rs.getString("href"));
            file.setName(rs.getString("img_name"));
            file.setDate(rs.getDate("date"));
            file.getTypeDocument().setId(rs.getInt("type_id"));
            file.getTypeDocument().setName(rs.getString("type_name"));
            file.getTypeDocument().setDocument_type(rs.getInt("document_type"));
            file.setValue_type_id(rs.getInt("value_type_id"));
            file.setUser_name((rs.getString("user_name")));
            return file;
        }
    };

    public void writeImage(MultipartFile src, String dtime) throws IOException {

        java.io.File compressedImageFile = new java.io.File(OBLDEVEL + dtime + src.getOriginalFilename());

        FileOutputStream os = new FileOutputStream(compressedImageFile);

        if (src.getOriginalFilename().toLowerCase().endsWith(".pdf")) {

            os.write(src.getBytes());
            os.flush();
            os.close();
            return;
        } else {

            float quality = 0.5f;

            // create a BufferedImage as the result of decoding the supplied InputStream

            BufferedImage image = ImageIO.read(src.getInputStream());
            int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image.getType();
            image = resizeImage(image, type);

            // get all image writers for JPG format
            Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");

            if (!writers.hasNext())
                throw new IllegalStateException("No writers found");

            ImageWriter writer = (ImageWriter) writers.next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();

            // compress to a given quality
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality);

            // appends a complete image stream containing a single image and
            //associated stream and image metadata and thumbnails to the output
            writer.write(null, new IIOImage(image, null, null), param);

            // close all streams
            os.close();
            ios.close();
            writer.dispose();
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g.dispose();

        return resizedImage;
    }

    public void saveImageToDB(Integer counter_id, Integer rem_id, Integer type_id, Date date, MultipartFile src, Integer value_type_id, String user_name) throws IOException {
        String dtime = Long.valueOf(new Date().getTime()) + "_";
        //System.out.println("pip_id=" + pip_id);
        String sql = "INSERT INTO dbo.files(counter_id, rem_id, img_name, [date], type_id, value_type_id, user_name) values(?,?,?,?,?,?,?) ";
        jdbcTemplate.update(sql, new Object[]{counter_id, rem_id, dtime + src.getOriginalFilename(), date, type_id, value_type_id, user_name});
        writeImage(src, dtime);
    }

    private boolean deleteFile(String filename) {
        java.io.File file = new java.io.File(OBLDEVEL + filename);
        return file.delete();
    }

    public void setRecordsList(List<Procedure> recordsList) {
        this.recordsList = recordsList;
    }

}
