package oe.roma.photodoc.services;

import oe.roma.photodoc.domain.Rem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service("remService")
public class RemService {
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Rem> getRems(){
        return jdbcTemplate.query("SELECT id, name from dbo.rem", remRowMapper);
    }

    RowMapper<Rem> remRowMapper = new RowMapper<Rem>() {
        public Rem mapRow(ResultSet rs, int rowNum) throws SQLException {
            Rem rem = new Rem();
            rem.setId(rs.getInt("id"));
            rem.setName(rs.getString("name"));
            return rem;
        }
    };
}
