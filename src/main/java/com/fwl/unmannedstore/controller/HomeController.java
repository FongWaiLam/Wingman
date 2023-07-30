package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private ProductService productService;

//    @Autowired
//    AuthenticationManager authenticationManager;

    @GetMapping("/usms")
    public String index(Model model) {

        log.info( "Logged in User: SecurityContextHolder" + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        int activeProductNo = productService.getActiveProductNo(true);
        model.addAttribute("productNo", activeProductNo);
        return "index";
    }
}
