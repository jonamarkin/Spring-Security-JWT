package io.jamapps.springsecurityjwt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JamAppsController {

    @RequestMapping({"/welcome"})
    public String welcome(){
        return "Welcome to JamApps";
    }
}
