package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }
@GetMapping("/error")
    public String errorView(){
        return "error";
}
@GetMapping("/result")
    public String resultView(){
        return "result";
    }
}
