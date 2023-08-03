package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.controller.requestResponse.Message;
import com.fwl.unmannedstore.controller.requestResponse.PaymentSalesRequest;
import com.fwl.unmannedstore.model.*;
import com.fwl.unmannedstore.service.*;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class PaymentSalesRestController {

    @Autowired
    private CartService cartService;
    @Autowired
    private RFIDService rfidService;
    @Autowired
    private ProductService productService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private SalesRecordService salesService;

    @PostMapping("/update_pay_sales_record")
    public ResponseEntity<Message> getProduct(@Valid @RequestBody PaymentSalesRequest request) {
        // Get RFID List
        List<RFID> rfidList = getRFIDList(request.getEpcList());
        // Create a new Cart for the sold item
        Cart cart = new Cart();
        cart.setRfidList(rfidList);
        cart.setAmount(request.getAmount());
        cartService.save(cart);
        log.info("cart ID: " + cart.getCartId());
        // Update Sold state of RFID
        updateRFIDSoldState(rfidList);
        // Update Payment Record
        Payment payment = new Payment();
        payment.setSuccessful(true);  // TODO: Receive state from terminal
        payment.setPaymentIntentId(request.getPaymentIntentId());
        paymentService.save(payment);
        log.info("pay ID: " + cart.getCartId());
        // Get Store
        Store store = storeService.getStoreById(request.getStoreId());
        // Update Sales Record
        SalesRecord salesRecord = new SalesRecord();
        salesRecord.setPayment(payment);
        // All API requests expect amounts to be provided in a currency’s smallest unit. For example, to charge 10 USD, provide an amount value of 1000 (that is, 1000 cents).
        // Stripe uses the smallest 0.01p = 1 unit
        int amountInPence = (int)request.getAmount()* 100;
        salesRecord.setAmount(amountInPence);
        salesRecord.setTransactionDateTime(payment.getPay_date_time());
        salesRecord.setStore(store);
        salesRecord.setCart(cart);
        salesService.save(salesRecord);

        Message message = new Message("RFID, Cart, Payment, Sales Record are all updated.");
        return ResponseEntity.ok(message);
    }

    // Helper Method
    public void updateRFIDSoldState(List<RFID> rfidList) {
        for (RFID rfid: rfidList) {
            rfidService.sold(rfid);
        }
    }
    // Helper Method
    public List<RFID> getRFIDList(List<String> epcList) {
        List<RFID> rfidList = new ArrayList<RFID>();
        for (String epc: epcList) {
            rfidList.add(rfidService.getRFIDByEPC(epc));
        }
        return rfidList;
    }
}