package oe.roma.photodoc.services;

import oe.roma.photodoc.domain.Rem;
import oe.roma.photodoc.domain.TypeDocument;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service("typeDocService")
public class TypeDocService {
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<TypeDocument> getTypes(){
        return jdbcTemplate.query("SELECT id, name, document_type from dbo.type_documents", typeRowMapper);
    }

    RowMapper<TypeDocument> typeRowMapper = new RowMapper<TypeDocument>() {
        public TypeDocument mapRow(ResultSet rs, int rowNum) throws SQLException {
            TypeDocument typeDocument = new TypeDocument();
            typeDocument.setId(rs.getInt("id"));
            typeDocument.setName(rs.getString("name"));
            typeDocument.setDocument_type(rs.getInt("document_type"));
            return typeDocument;
        }
    };


}
