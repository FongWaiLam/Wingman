package com.fwl.unmannedstore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usms")
public class ConstructionController {
    @GetMapping("/sales_trend")
    public String salesTrend() {
        return "under_construction";
    }

    @GetMapping("/support")
    public String support() {
        return "under_construction";
    }

    @GetMapping("/about")
    public String about() {
        return "under_construction";
    }
    @GetMapping("/business")
    public String businessProfile() {
        return "under_construction";
    }


}
