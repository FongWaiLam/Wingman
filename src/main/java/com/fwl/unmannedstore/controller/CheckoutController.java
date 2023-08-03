package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.service.RFIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private RFIDService rfidService;

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }



}
