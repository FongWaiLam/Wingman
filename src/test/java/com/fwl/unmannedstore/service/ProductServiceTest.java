package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.respository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        Product product1 = Product.builder()
                .name("Suprema White Hotel Soft Towel")
                .price(8.99)
                .description("100% cotton 60x100cm")
                .category("Bathroom")
                .photo("white_hotel_towel.jpg")
                .isActive(true)
                .creation_date(new Timestamp(System.currentTimeMillis()))
                .prodId(1)
                .build();
        Product product2 = Product.builder()
                .name("Butterfly Wall Decorations")
                .price(3.99)
                .description("Total 6pcs butterfly stickers are in 4 sizes(2.4inches-8pcs, 3.15inches-8pcs, 3.9inches-4pcs, 4.7inches-4pcs). and 12 different styles (as picture showed), rich and charming, It will Make your home more wonderful and comfortable.")
                .category("Home & Living")
                .photo("butterfly_wall-deco.jpg")
                .isActive(true)
                .creation_date(new Timestamp(System.currentTimeMillis()))
                .prodId(2)
                .build();
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        Mockito.when(productRepository.findById(1)).thenReturn(Optional.ofNullable(product1));
        Mockito.when(productRepository.findAll()).thenReturn(productList);
        Mockito.when(productRepository.findByIsActive(true)).thenReturn(productList);
    }

    @Test
    void getActiveProductNo() {
        int noOfActiveProduct = productService.getActiveProductNo(true);
        assertEquals(2, noOfActiveProduct);
    }

    @Test
    void getProductById() {
        int productId = 1;
        Product product = productService.getProductById(productId);
        assertEquals(productId, product.getProdId());
    }

    @Test
    void deactivateById() {
        productService.deactivateById(1);
        Product product = productService.getProductById(1);
        assertEquals(false, product.isActive());
    }

    @Test
    void deleteById() {
        productService.deleteById(2);
        assertThrows(NoSuchElementException.class, () -> {
            productService.getProductById(2);
        });
    }
}