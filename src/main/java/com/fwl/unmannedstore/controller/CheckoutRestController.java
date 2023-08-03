package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.controller.requestResponse.CheckoutScanRequest;
import com.fwl.unmannedstore.controller.requestResponse.CheckoutScanResponse;
import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.service.RFIDService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutRestController {

    @Autowired
    private RFIDService rfidService;

    @PostMapping("/get_product")
    public ResponseEntity<CheckoutScanResponse> getProduct(@Valid @RequestBody CheckoutScanRequest request) {
        RFID rfid = rfidService.getRFIDByEPC(request.getEpc());
        Product product = rfid.getProduct();
        CheckoutScanResponse scanResponse = new CheckoutScanResponse(request.getEpc(), product.getProdId(), product.getName(), product.getPrice(), product.getPhoto());
        return ResponseEntity.ok(scanResponse);
    }
}
