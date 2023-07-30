package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usms")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/add_store")
    public String addStore() {
        return"add_store";
    }

    @PostMapping("/save_store")
    public String saveStore(@ModelAttribute Store store) {
        storeService.save(store);
        return"redirect:/usms";
    }
}
