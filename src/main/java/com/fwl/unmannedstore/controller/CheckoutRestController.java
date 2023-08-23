package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.controller.requestResponse.CheckoutScanRequest;
import com.fwl.unmannedstore.controller.requestResponse.CheckoutScanResponse;
import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.service.RFIDService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@Slf4j
public class CheckoutRestController {

    @Autowired
    private RFIDService rfidService;


    @PostMapping("/get_product")
    public ResponseEntity<CheckoutScanResponse> getProduct(@Valid @RequestBody CheckoutScanRequest request) {
        log.info("/get_product enter now");
        RFID rfid = rfidService.getRFIDByEPC(request.getEpc());
        if (rfid.isSold()) {
            CheckoutScanResponse message = new CheckoutScanResponse();
            log.info("Product already sold!");
            message.setConflictMessage("Product already sold!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }
        Product product = rfid.getProduct();
        CheckoutScanResponse scanResponse = new CheckoutScanResponse(request.getEpc(), product.getProdId(), product.getName(), product.getPrice(), product.getPhoto());
        log.info("/get_product return now");
        return ResponseEntity.ok(scanResponse);
    }
}
