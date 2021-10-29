package io.jamapps.springsecurityjwt;

import io.jamapps.springsecurityjwt.filters.JwtRequestFilter;
import io.jamapps.springsecurityjwt.services.MyUserDetailsService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.security.authentication..ShaPasswordEncoder;

import javax.sql.DataSource;
import java.util.Locale;

@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        //MessageDigestPasswordEncoder messageDigestPasswordEncoder = new MessageDigestPasswordEncoder("SHA-1");
//       String encoded =  DigestUtils.sha1Hex("1234");
        PasswordEncoder passwordEncoder  = NoOpPasswordEncoder.getInstance();
//        System.out.println(encoded.toUpperCase());
//        System.out.println("sodspdospdospd");
        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(myUserDetailsService);
//
//    }


//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        DelegatingPasswordEncoder encoder = new DelegatingPasswordEncoder();
////        auth.userDetailsService(myUserDetailsService).passwordEncoder(encoder);
////    }
//


//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .dataSource(dataSource)
//                .usersByUsernameQuery("select user_id, user_pass1, enabled from user_profile_o where user_id=?")
//    .authoritiesByUsernameQuery("select user_id, f_login from user_profile_o where username=?")
//
//        ;
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests().antMatchers("/authenticate").permitAll()
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
