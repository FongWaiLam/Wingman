package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.CheckoutState;
import com.fwl.unmannedstore.model.SalesRecord;
import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.service.SalesRecordService;
import com.fwl.unmannedstore.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class SalesController {

    @Autowired
    private SalesRecordService salesRecordService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CheckoutState checkoutState;


    @GetMapping("/sales_records")
    public String getSalesRecords(Model model) {
        List<SalesRecord> salesRecords = salesRecordService.getAllSalesRecords();
        model.addAttribute("salesRecords", salesRecords);
        return "sales_records";
    }

    @PostMapping("/record_purchase/{storeId}")
    public String recordPurchase(int storeId) {
        Store store = storeService.getStoreById(storeId);
        SalesRecord salesRecord = SalesRecord.builder()
                .cart(checkoutState.getCurrentCart())
                .payment(checkoutState.getCurrentPayment())
                .amount(checkoutState.getCurrentCart().getAmount())
                .store(store)
                .build();
        salesRecordService.save(salesRecord);
        checkoutState.clear();
        return"redirect:/checkout";
    }
}
