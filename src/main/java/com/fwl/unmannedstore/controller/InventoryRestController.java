package com.fwl.unmannedstore.controller;


import com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay;
import com.fwl.unmannedstore.service.RFIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usms")
public class InventoryRestController {

    @Autowired
    private RFIDService rfidService;

    @GetMapping("/inventory/{category}")
    public ResponseEntity<List<InventoryDisplay>> getCategoryInventory(@PathVariable("category") String category) {
        List<InventoryDisplay> inventoryDisplayList = rfidService.getAllUnsoldInventoryDisplayByCategory(category);
        return ResponseEntity.ok(inventoryDisplayList);
    }

    @GetMapping("/inventory/all")
    public ResponseEntity<List<InventoryDisplay>> getFullInventory() {
        List<InventoryDisplay> inventoryData = rfidService.getAllUnsoldInventoryDisplay();
        return ResponseEntity.ok(inventoryData);
    }
    @GetMapping("/inventory/pie_chart")
    public ResponseEntity<List<InventoryDisplay>> getPieChart() {
        List<InventoryDisplay> inventoryData = rfidService.getAllUnsoldInventoryDisplayGroupCatIgnoreStore();
        return ResponseEntity.ok(inventoryData);
    }
}
