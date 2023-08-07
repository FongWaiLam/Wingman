package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.respository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Get the full Product List/ Inventory List
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get the full Product List/ Inventory List
    public int getActiveProductNo(boolean isActive) {
        return productRepository.findByIsActive(isActive).size();
    }

    // Save a Product
    public void save(Product product) {
        productRepository.save(product);
    }

    // Deactivate a product
    public void deactivateById(int prod_id) {
        log.info("deactivateById(int prod_id) executed");
        Product product = productRepository.findById(prod_id).get();
        product.setActive(false);
        save(product);
    }

    // Get a specific product (For update)
    public Product getProductById(int prod_id) {
        return productRepository.findById(prod_id).get();
    }

    // Delete a product
    public void deleteById(int prod_id) {
        productRepository.deleteById(prod_id);
    }

}
