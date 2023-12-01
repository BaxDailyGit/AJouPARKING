package com.example.ajouparking.controller.routing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/auth/signin")
    public String signin(){
        return "signin";
    }

}
