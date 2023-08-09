package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay;
import com.fwl.unmannedstore.controller.requestResponse.SalesDisplay;
import com.fwl.unmannedstore.service.SalesRecordService;
import com.fwl.unmannedstore.service.StoreService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/usms")
public class SalesRestController {
    @Autowired
    private SalesRecordService salesRecordService;
    @Autowired
    private StoreService storeService;

//    @GetMapping("/inventory/{category}")
//    public ResponseEntity<List<InventoryDisplay>> getCategoryStore(@PathVariable("category") String category) {
//        List<InventoryDisplay> inventoryDisplayList = rfidService.getAllUnsoldInventoryDisplayByCategory(category);
//        return ResponseEntity.ok(inventoryDisplayList);
//    }

    @GetMapping("/sales/line-chart")
    public ResponseEntity<List<SalesDisplay>> getMonthlyInventory() {
        List<SalesDisplay> inventoryData = salesRecordService.getSalesByMonth();
        return ResponseEntity.ok(inventoryData);
    }
//    @GetMapping("/inventory/pie_chart")
//    public ResponseEntity<List<InventoryDisplay>> getPieChart() {
//        List<InventoryDisplay> inventoryData = rfidService.getAllUnsoldInventoryDisplayGroupCatIgnoreStore();
//        return ResponseEntity.ok(inventoryData);
//    }
}
