package com.fwl.unmannedstore.controller;


import com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay;
import com.fwl.unmannedstore.service.RFIDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usms")
@Slf4j
public class InventoryRestController {

    @Autowired
    private RFIDService rfidService;

    @GetMapping("/inventory/{category}")
    public ResponseEntity<List<InventoryDisplay>> getCategoryInventory(@PathVariable("category") String category) {
        log.info("Category Query: " + category);
        List<InventoryDisplay> inventoryDisplayList = rfidService.getAllUnsoldInventoryDisplayByCategory(category);
        return ResponseEntity.ok(inventoryDisplayList);
    }

    @GetMapping("/inventory/store/{storeId}")
    public ResponseEntity<List<InventoryDisplay>> getStoreInventory(@PathVariable("storeId") int storeId) {
        List<InventoryDisplay> inventoryDisplayList = rfidService.getAllUnsoldInventoryDisplayByStore(storeId);
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


    @GetMapping("/inventory/bar_chart")
    public ResponseEntity<List<InventoryDisplay>> getBarChart() {
        List<InventoryDisplay> inventoryData = rfidService.getAllUnsoldInventoryDisplayByStore();
        for (InventoryDisplay inventoryDisplay : inventoryData) {
            log.info("Inventory Display: " + inventoryDisplay);
        }
        return ResponseEntity.ok(inventoryData);
    }

}
