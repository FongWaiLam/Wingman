package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.security.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        // Create a mock data
        Product product = Product.builder()
                .name("Luggage Cover")
                .price(22)
                .category("Travel")
                .description("Let's go travel!")
                .isActive(true)
                .photo("luggage_cover.jpg")
                .build();
        testEntityManager.persist(product);
    }

    @Test
    public void findAllTest() {
        List<Product> products = productRepository.findAll();
        int noOfProducts = products.size();
        Product product = products.get(noOfProducts - 1);
        assertEquals("Luggage Cover", product.getName());
    }
    @Test
    public void findByIsActiveTest() {
        List<Product> products = productRepository.findByIsActive(true);
        Product product = products.get(0);
        assertEquals(true, product.isActive());
    }
}