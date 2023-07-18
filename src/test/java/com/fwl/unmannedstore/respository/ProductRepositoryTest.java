package com.fwl.unmannedstore.respository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    @Test
    public void printQuantity() {
        System.out.println("Quantity of Product ID 2 = " + productRepository.findById(2).get().getQuantity());
    }

}