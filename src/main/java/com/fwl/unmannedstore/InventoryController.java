package com.fwl.unmannedstore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InventoryController {

    @GetMapping("inventory")
    public String home() {
        return "This is the home page for Inventory Management.";
    }
}
