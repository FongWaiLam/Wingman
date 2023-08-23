package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.controller.requestResponse.SalesDisplay;
import com.fwl.unmannedstore.service.ProductService;
import com.fwl.unmannedstore.service.SalesRecordService;
import com.fwl.unmannedstore.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SalesRecordService salesRecordService;

    @Autowired
    private StoreService storeService;
//    @Autowired
//    AuthenticationManager authenticationManager;

    @GetMapping("/usms")
    public String index(Model model) {

        log.info( "Logged in User: SecurityContextHolder" + SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        int activeProductNo = productService.getActiveProductNo(true);
        int activeStoreNo = storeService.getStoreNoByStatus("normal");
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        log.info("Date: " + date);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        log.info("Year: " + year + " Month: " + month);
        double salesMonthToDate = salesRecordService.getSalesByAMonth(year, month);
        String salesMonthToDateString = String.format("%.2f", salesMonthToDate);
        model.addAttribute("activeProductNo", activeProductNo);
        model.addAttribute("activeStoreNo", activeStoreNo);
        model.addAttribute("salesMonthToDateString", salesMonthToDateString);
        return "index";
    }

}
