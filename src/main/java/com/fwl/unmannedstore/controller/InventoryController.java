package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay;
import com.fwl.unmannedstore.service.ProductService;
import com.fwl.unmannedstore.service.RFIDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/usms")
public class InventoryController {
    @Autowired
    private RFIDService rfidService;

    @Autowired
    private ProductService productService;

    @GetMapping("/inventory")
    public String getCategoryInventory(Model model) {
        List<InventoryDisplay> inventoryDisplayList = rfidService.getAllUnsoldInventoryDisplay();
        for (InventoryDisplay i: inventoryDisplayList) {
            log.info(i.getProdId() + " " + i.getProdName() + " " + i.getCategory() + " " + i.getTotalAmount() + " " + i.getTotalValue());
        }
        model.addAttribute("products", inventoryDisplayList);
        return "inventory";
    }
}
