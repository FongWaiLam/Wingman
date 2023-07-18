package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Cart;
import com.fwl.unmannedstore.respository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private CartRepository cartRepository;

    // Get the full cart List
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    // Get a specific cart (For update)
    public Cart getCartById(int cartId) {
        return cartRepository.findById(cartId).get();
    }

    // Add a new cart or Update an existing cart
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    // Delete a cart
    public void deleteById(int cartId) {
        cartRepository.deleteById(cartId);
    }
}
