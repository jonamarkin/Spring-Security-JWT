package io.jamapps.springsecurityjwt.services;

import io.jamapps.springsecurityjwt.models.UserDAO;
import io.jamapps.springsecurityjwt.models.UserModel;
import io.jamapps.springsecurityjwt.rowmappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Select from the database
//        String query = "select user_id, user_pass1 from user_profile_o where user_id = ?";
//
//        Object[] args = new Object[]{username};
//        List<UserModel> userModel = jdbcTemplate.query(query, args, new UserRowMapper());
//
//        System.out.println(userModel.get(0).toString());
//
//
//        System.out.println("Heyyyyyyyyyyyyyyyyyy");
//
//        return new User(userModel.get(0).getUserId(), userModel.get(0).getPassword(), new ArrayList<>());

//        return new User("foo", "foo", new ArrayList<>());


        UserModel userInfo = userDAO.getUserInfo(username.toUpperCase());
        GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole());
        UserDetails userDetails = new User(userInfo.getUserId(),
                userInfo.getPassword(), Arrays.asList(authority));

        System.out.println(userDetails);
        return userDetails;
    }
}
