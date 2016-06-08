package oe.roma.photodoc.services;

/**
 * Created by us9522 on 25.08.2015.
 */
import oe.roma.photodoc.domain.User;
import oe.roma.photodoc.utils.ADAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by us8610 on 26.12.2014.
 */
@Service
public class UserService  {

    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private ADAuthenticator authenticator;
    ////////////
    private JdbcTemplate jdbcTemplateSec;
    ///////////
    @Resource(name = "dataSource")
    private void setDataSource(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }


    //////////
    @Resource(name = "dataSource")
    private void setDataSourceSec(DataSource dataSource) {
        jdbcTemplateSec = new JdbcTemplate(dataSource);
    }
    ////////////


    public boolean checkAuthority(String login) {
        String sql = "SELECT COUNT(*) FROM users where login=:login and enabled=1";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("login", login);
        Integer count = jdbcTemplate.queryForObject(sql, parameters, Integer.class);
        return count > 0;
    }

    public List<User> getUsers(){
        return jdbcTemplateSec.query("SELECT * from dbo.users", usersRowMapper);
    }

    public User getUser(String login){
        String sql = "SELECT u.*, r.id as r_id, r.name as rem_name, full_name, email " +
                "from dbo.users u " +
                "left join dbo.rem r " +
                "on u.rem_id=r.id " +
                "where u.login=?";
        try{
            return jdbcTemplateSec.queryForObject(sql, new Object[]{login}, usersRowMapper);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    RowMapper<User> usersRowMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setLogin(rs.getString("login"));
            user.getRem().setId(rs.getInt("r_id"));
            user.getRem().setName(rs.getString("rem_name"));
            user.setPermission(rs.getInt("permission"));
            user.setFull_name(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getInt("role"));
            user.setTab_number(rs.getInt("tab_number"));
            user.setViddil(rs.getString("viddil"));
            return user;
        }
    };

    public User getUser(String login, String password) throws NamingException {
        Map<String, String> map = authenticator.authenticate(login, password);
        if (map == null) {
            return null;
        }
        String tlogin = map.get("sAMAccountName");
        String email = map.get("mail");
        String name = map.get("displayName");
        return new User(tlogin, name, email);
        /////////////////////////////////////////////////////
    }
}
