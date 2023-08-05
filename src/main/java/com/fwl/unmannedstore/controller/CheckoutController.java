package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.service.RFIDService;
import com.fwl.unmannedstore.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CheckoutController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        List<Store> storeList = storeService.getAllStores();
        model.addAttribute("storeList", storeList);
        return "checkout_store";
    }

    @GetMapping("/checkout/{storeId}")
    public String checkout(@PathVariable("storeId") int storeId) {
        return "checkout";
    }

}
