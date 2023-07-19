package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.CheckoutState;
import com.fwl.unmannedstore.model.Payment;
import com.fwl.unmannedstore.service.PaymentService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Getter
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private CheckoutState checkoutState;

    @PostMapping("/checkout/payment")
    public String paymentProceed(){
        checkoutState.getNewPayment();
        return"payment_result";
    }
}
