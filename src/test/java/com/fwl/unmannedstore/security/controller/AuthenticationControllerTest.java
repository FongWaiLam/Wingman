package com.fwl.unmannedstore.security.controller;

import com.fwl.unmannedstore.security.UserRepository;
import com.fwl.unmannedstore.security.authentication.RegisterRequest;
import com.fwl.unmannedstore.security.config.AuthenticationService;
import com.fwl.unmannedstore.security.entity.Role;
import com.fwl.unmannedstore.security.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationControllerTest {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void registerAcc() {
        RegisterRequest registerRequest = new RegisterRequest("mary", "lam", "marylam@gmail.com", "marylam", Role.ROLE_ADMIN);
        String profilePhoto = "C:\\Lenovo Notebook\\Waiwai lenovo N.B\\UK\\Postgrad\\Final Project\\gitlab\\unmanned-store\\profile(white) - W1.png";
        User user = authenticationService.register(registerRequest);
        user.setProfile(profilePhoto);
        userRepository.save(user);
    }

}