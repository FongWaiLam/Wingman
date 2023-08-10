package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.model.SalesRecord;
import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.service.SalesRecordService;
import com.fwl.unmannedstore.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/usms")
public class SalesController {

    @Autowired
    private SalesRecordService salesRecordService;
    @Autowired
    private StoreService storeService;


    @GetMapping("/sales_records")
    public String getSalesRecords(Model model) {
        List<SalesRecord> salesRecords = salesRecordService.getAllSalesRecords();
        List<Store> storeList = storeService.getAllStores();
        model.addAttribute("salesRecords", salesRecords);
        model.addAttribute("storeList", storeList);
        return "sales_records";
    }
}
