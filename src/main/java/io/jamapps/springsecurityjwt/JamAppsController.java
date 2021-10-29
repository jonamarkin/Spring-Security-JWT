package io.jamapps.springsecurityjwt;

import io.jamapps.springsecurityjwt.models.AuthenticationRequest;
import io.jamapps.springsecurityjwt.models.AuthenticationResponse;
import io.jamapps.springsecurityjwt.models.BaseResponse;
import io.jamapps.springsecurityjwt.services.MyUserDetailsService;
import io.jamapps.springsecurityjwt.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class JamAppsController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    JwtUtil jwtUtil;

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ResponseEntity<?> welcome(){
        //final String s = "Welcome to JamApps";
        return ResponseEntity.ok(new BaseResponse("000", "Success", "Welcome to JamApps"));
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            String encoded =  DigestUtils.sha1Hex(authenticationRequest.getPassword());

            System.out.println(encoded.toUpperCase());
            String passwordEncoded = encoded.toUpperCase();
            System.out.println("Password encoded");
            System.out.println(passwordEncoded);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), passwordEncoded)
            );
        }catch (BadCredentialsException e){
            throw new Exception("Incorrect username and passwodrd", e);
        }

        final UserDetails userDetails = myUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new BaseResponse("000", "User authenticated", new AuthenticationResponse(jwt)));
    }
}
