package oe.roma.photodoc.services;

import oe.roma.photodoc.domain.Customer;
import oe.roma.photodoc.domain.CustomerObject;
import oe.roma.photodoc.domain.File;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by us8610 on 19.06.14.
 */
@Transactional
@Service("reportService")
@SuppressWarnings("unchecked")
public class ReportService {

    private SimpleJdbcCall jdbcCall;


    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcCall = new SimpleJdbcCall(dataSource)
                                        .withSchemaName("dbo")
                                        .withProcedureName("ReportAll")
                                        .returningResultSet("records", reportRowMapper);
    }

    public List<Customer> recordsList(Integer rem_id){
        final MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("rem_id",rem_id);
        Map<String, Object> results = jdbcCall.execute(params);
        return (List) results.get("records");
    }




    RowMapper<Customer> reportRowMapper = new RowMapper<Customer>() {
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            Customer customer = new Customer();
            CustomerObject object = new CustomerObject();
            File file = new File();
            List<CustomerObject> objectList = new ArrayList<CustomerObject>();
            List<File> files = new ArrayList<File>();

            customer.setContract_number(rs.getString("ContractNumber"));

            customer.setName(rs.getString("ShortName"));
            object.setCounter_number(rs.getString("CounterNumber"));
            object.setName(rs.getString("Name"));

            object.setAddress(rs.getString("FullAddress"));
            object.setInspector(rs.getString("SegmentName"));

            file.setDate(rs.getDate("date"));
            file.setName(rs.getString("img_name"));
            file.getTypeDocument().setName(rs.getString("type_document"));
            file.getDepartment().setName(rs.getString("rem_name"));
            file.setUser_name(rs.getString("user_name"));
            files.add(file);

            object.setFiles(files);
            objectList.add(object);

            customer.setObjects(objectList);

            return customer;
        }
    };

}
