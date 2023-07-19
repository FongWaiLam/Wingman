package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.service.RFIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class RFIDController {
    @Autowired
    private RFIDService rfidService;

    @GetMapping("/add_rfid")
    public String addProduct() {
        return "add_rfid";
    }

    @PostMapping("/save_rfid_list")
    public String saveRFID(@ModelAttribute List<RFID> rfidList) {
        for(RFID rfid: rfidList) {
            rfidService.save(rfid);
        }
        return"redirect:/product/{prodId}";
    }
    @PostMapping("/save_rfid")
    public String saveRFID(@ModelAttribute RFID rfid) {
            rfidService.save(rfid);
        return"redirect:/product/{prodId}";
    }

    @GetMapping("/delete_rfid/{epc}")
    public String deleteRFIDByEPC(@PathVariable String epc) {
        Product product = rfidService.getRFIDByEPC(epc).getProduct();
        rfidService.deleteByEPC(epc);
        return "redirect:/product/{product.prodId}";
    }

    @GetMapping("/inventory")
    public ModelAndView getInventory() {
        List<RFID> rfidList = rfidService.getAllRFIDs();
        return new ModelAndView("inventory", "rfidList", rfidList);
    }

}
