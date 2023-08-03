//package com.fwl.unmannedstore;
//
//import com.fwl.unmannedstore.model.Cart;
//import com.fwl.unmannedstore.model.Payment;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.stereotype.Component;
//
//@Component
//@Getter
//@Setter
//public class CheckoutState {
//
//    private Cart currentCart;
//    private Payment currentPayment;
//
//    public void clearCart(){
//        currentCart = null;
//    }
//
//    public void getNewCart(){
//        currentCart = new Cart();
//    }
//
//    public void addNewValueToCart(double amount) {
//        currentCart.setAmount(currentCart.getAmount() + amount);
//    }
//
//    public void clearPayment(){
//        currentPayment = null;
//    }
//    public void getNewPayment() {
//        currentPayment = new Payment();
//    }
//    public void clear(){
//        clearCart();
//        clearPayment();
//    }
//
//
//}
