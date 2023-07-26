package com.fwl.unmannedstore.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


//@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/usms")
public class TestController {
//    @GetMapping("/all")
//    public String allAccess() {
//        return "Public Content.";
//    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
//
//    @GetMapping("/user")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public String userAccess() {
//        return "User Content.";
//    }
//
//    @GetMapping("/admin")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String adminAccess() {
//        return "Admin Board.";
//    }
}