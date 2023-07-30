package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.service.ProductService;
import com.fwl.unmannedstore.service.RFIDService;
import com.fwl.unmannedstore.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/usms")
public class RFIDController {
    @Autowired
    private RFIDService rfidService;

    @Autowired
    private ProductService productService;

    @Autowired
    private StoreService storeService;

    @GetMapping("/product/{prodId}/add_rfid")
    public String addProduct(@PathVariable("prodId") int prodId, Model model) {
        Product product = productService.getProductById(prodId);
        List<Store> storeList = storeService.getAllStores();
        model.addAttribute("storeList", storeList);
        model.addAttribute("product", product);
        return "add_rfid";
    }

    @PostMapping("/save_rfid_list")
    public String saveRFIDList(@RequestBody AddRFIDRequest request) {
        Product product = productService.getProductById(request.getProdId());
        Store store = storeService.getStoreById(request.getStoreId());

        for(String epc: request.getEpcList()) {
            RFID rfid = new RFID(epc,product, false, store);
            rfidService.save(rfid);
        }
        return "redirect:/usms";
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
