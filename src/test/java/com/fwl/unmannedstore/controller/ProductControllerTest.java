//package com.fwl.unmannedstore.controller;
//
//import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobServiceClient;
//import com.fwl.unmannedstore.service.ProductService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import org.springframework.test.web.servlet.MockMvc;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//
//
//@SpringBootTest
//@DataJpaTest
//class ProductControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProductService productService;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    @MockBean
//    private BlobServiceClient blobServiceClient;
//
//    @MockBean
//    private BlobContainerClient blobContainerClient;
//
//    @MockBean
//    private BlobClient blobClient;
//
//    @Test
//    public void addProduct() throws Exception {
//        mockMvc.perform(get("/usms/add_product"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("add_product"));
//    }
//
////
////
////    @Test
////    void savePhoto() {
////    }
////
////    @Test
////    void testAddProduct() {
////    }
////
////    @Test
////    void getProducts() {
////    }
////
////    @Test
////    void getProduct() {
////    }
////
////    @Test
////    void showUpdateProductForm() {
////    }
////
////    @Test
////    void updateProductById() {
////    }
////
////    @Test
////    void deleteProductById() {
////    }
//}