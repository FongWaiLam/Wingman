package com.fwl.unmannedstore.security.controller;

import com.fwl.unmannedstore.security.UserRepository;
import com.fwl.unmannedstore.security.entity.Role;
import com.fwl.unmannedstore.security.entity.User;
import com.fwl.unmannedstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
@RequestMapping("/usms")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("new_user")
    public String newUserForm(Model model) {
        Role[] allRoles = Role.values();
        model.addAttribute("allRoles", allRoles);
        return"register";
    }

    @GetMapping("users")
    public String allUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return"users";
    }


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