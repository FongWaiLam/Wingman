package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.CheckoutState;
import com.fwl.unmannedstore.model.Cart;
import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.service.CartService;
import com.fwl.unmannedstore.service.ProductService;
import com.fwl.unmannedstore.service.RFIDService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Getter
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private RFIDService rfidService;
    @Autowired
    private ProductService productService;

    @Autowired
    private CheckoutState checkoutState;

    @PostMapping("/credit_card")
    public String addNewCart() {
        checkoutState.getNewCart();
        cartService.save(checkoutState.getCurrentCart());
        return"redirect:/checkout/scan";
    }

    @PostMapping("/scan/{epc}")
    public String addRFIDToCart(String epc) {
        // If cart is null, the credit card is not presented.
        if (checkoutState.getCurrentCart() == null) {
            return"redirect:/checkout/"; // Go back to scan credit card
        }
        // Retrieve RFID
        RFID rfid = rfidService.getRFIDByEPC(epc);
        checkoutState.getCurrentCart().addRFID(rfid);
        // Retrieve Product
        Product product = productService.getProductById(rfid.getProduct().getProdId());
        // Calculate the cart value
        checkoutState.addNewValueToCart(product.getPrice());
        return"redirect:/checkout/scan";
    }

    // Customer confirmed scanned all products
    @PostMapping("/scan_complete")
    public String scanComplete(Model model){
        cartService.save(checkoutState.getCurrentCart());
        double amount = checkoutState.getCurrentCart().getAmount();
        model.addAttribute("amount", amount);
        return"redirect:/checkout/payment"; // Proceed to payment
    }

}
