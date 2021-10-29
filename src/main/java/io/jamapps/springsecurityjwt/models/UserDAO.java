package io.jamapps.springsecurityjwt.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAO {
    private JdbcTemplate jdbcTemplate;
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public UserModel getUserInfo(String username){
        String sql = "SELECT user_id name, user_pass1 pass from user_profile_o WHERE user_id = ?";
        UserModel userInfo = jdbcTemplate.queryForObject(sql, new Object[]{username},
                (rs, rowNum) -> {
                    UserModel user = new UserModel();
                    user.setUserId(rs.getString("name"));
                    user.setPassword(rs.getString("pass"));
                    user.setRole("role");
                    return user;
                });
        return userInfo;
    }
}
