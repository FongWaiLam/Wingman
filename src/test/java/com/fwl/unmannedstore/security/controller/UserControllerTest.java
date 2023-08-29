//package com.fwl.unmannedstore.security.controller;
//
//import com.fwl.unmannedstore.security.authentication.JwtService;
//import com.fwl.unmannedstore.security.config.JwtAuthenticationFilter;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@Import(SecurityConfig.class)
//@WebMvcTest(UserController.class)
//class UserControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    @MockBean
//    private JwtService jwtService;
//
//    @Test
//    void shouldReturnLoginPage() throws Exception {
//        this.mockMvc
//                .perform(MockMvcRequestBuilders.get("/usms/login")
//                        )
//                .andExpect(status().is(200));
//    }
//}