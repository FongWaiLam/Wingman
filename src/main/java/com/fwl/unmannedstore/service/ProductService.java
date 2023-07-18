package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.respository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    // Get the full Product List/ Inventory List
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Save a Product
    public void save(Product product) {
        productRepository.save(product);
    }

    // Deactivate a product
    public void deactivate(int prod_id) {
        productRepository.findById(prod_id).get().setActive(false);
    }

    // Get a specific product (For update)
    public void getProductById(int prod_id) {
        productRepository.findById(prod_id).get();
    }

    // Delete a product
    public void deleteById(int prod_id) {
        productRepository.deleteById(prod_id);
    }

}
