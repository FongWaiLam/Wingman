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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/usms")
public class SalesRestController {
    @Autowired
    private SalesRecordService salesRecordService;
    @Autowired
    private StoreService storeService;


    @GetMapping("/sales/line-chart")
    public ResponseEntity<List<SalesDisplay>> getMonthlyInventory() {
        List<SalesDisplay> inventoryData = salesRecordService.getSalesByMonth();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int currentYear = timestamp.toLocalDateTime().getYear();
        int currentMonth = timestamp.toLocalDateTime().getMonthValue();
        // Create list of empty records
        List<SalesDisplay> thisYearInventoryData = new ArrayList<>();
        for(int i = 1; i <= currentMonth; i++) {
            thisYearInventoryData.add(new SalesDisplay(currentYear,i,0));
        }
        // Set sales of this year to the list
        for (SalesDisplay data: inventoryData) {
            if (data.getYear() == currentYear) {
                thisYearInventoryData.set(data.getMonth() - 1, data);
            }
        }
        return ResponseEntity.ok(thisYearInventoryData);
    }
}
