package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.controller.requestResponse.PaymentIntentRequest;
import com.fwl.unmannedstore.service.PaymentService;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.terminal.ConnectionToken;
import com.stripe.model.terminal.Location;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.terminal.ConnectionTokenCreateParams;
import com.stripe.param.terminal.LocationCreateParams;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Getter
@Slf4j
public class PaymentRestController {
    @Autowired
    private PaymentService paymentService;

    static {
        Stripe.apiKey = "sk_test_51NJ0KXCeKXiTFd6o4C23MhkmVMVPaPGpEF4jLSH9NJOftMu0oJag4GszFCQyFC6dQ1T3YQ0a60nR8wcwtzFvFa5T00z5El6iWG";
    }

    private static Gson gson = new Gson();

    // Connect to terminal
    @PostMapping("/connection_token")
    public String connectToken() throws StripeException {
        log.info("connectToken() entered");
        ConnectionTokenCreateParams params = ConnectionTokenCreateParams.builder()
                .build();

        ConnectionToken connectionToken = ConnectionToken.create(params);

        Map<String, String> map = new HashMap();
        log.info("connectionToken.getSecret(): " + connectionToken.getSecret());
        map.put("secret", connectionToken.getSecret());
        return gson.toJson(map);
    }


    @PostMapping("/create_payment_intent")
    public String createPaymentIntent(@RequestBody PaymentIntentRequest paymentIntentRequest)
            throws StripeException {
        // For Terminal payments, the 'payment_method_types' parameter must include
        // 'card_present'.
        // To automatically capture funds when a charge is authorized,
        // set `capture_method` to `automatic`.
        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setCurrency("gbp")
                // Actual amount or deposit
                .setAmount(paymentIntentRequest.getAmount())
                // Hold the amount larger than the actual purchase - 50pounds - security deposit
                // (At the time when the customer enter the shop)
                .setCaptureMethod(PaymentIntentCreateParams.CaptureMethod.MANUAL)
                .addPaymentMethodType("card_present")
                .build();
        // Create a PaymentIntent with the order amount and currency
        PaymentIntent intent = PaymentIntent.create(createParams);

        return intent.toJson();
    }


    @PostMapping("/capture_payment_intent")
    public String capturePaymentIntent(@RequestBody PaymentIntentRequest paymentIntentRequest)
            throws StripeException {
        log.info("PaymentIntentRequest: " + paymentIntentRequest);
        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentRequest.getPaymentIntentId());
        log.info("Intent: " + intent);
        // Capture amount smaller than the original create amount
//        PaymentIntentCaptureParams params =
//                PaymentIntentCaptureParams.builder()
//                        .setAmountToCapture(750L)
//                        .build();
//        intent = intent.capture(params);
        intent = intent.capture();
        return intent.toJson();
    }

    public static Location createLocation() throws StripeException{
        LocationCreateParams.Address address =
                LocationCreateParams.Address.builder()
                        .setLine1("200 Byres Rd")
                        .setCity("Glasgow")
                        .setCountry("GB")
                        .setPostalCode("G12 8UQ")
                        .build();

        LocationCreateParams params =
                LocationCreateParams.builder()
                        .setDisplayName("HQ")
                        .setAddress(address)
                        .build();

        Location location = Location.create(params);

        return location;
    }
}
