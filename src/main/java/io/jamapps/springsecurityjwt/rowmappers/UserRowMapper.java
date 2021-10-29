package io.jamapps.springsecurityjwt.rowmappers;

import io.jamapps.springsecurityjwt.models.UserModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRowMapper implements RowMapper<UserModel> {
    @Override
    public UserModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserModel userModel = new UserModel();

        userModel.setUserId(rs.getObject("USER_ID")== null ? " " :rs.getString("USER_ID"));
        userModel.setPassword(rs.getObject("USER_PASS1")== null ? " " :rs.getString("USER_PASS1"));
        userModel.setRole("sd");

        return null;
    }
}
