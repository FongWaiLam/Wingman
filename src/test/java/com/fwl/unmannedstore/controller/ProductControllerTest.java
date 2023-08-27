//package com.fwl.unmannedstore.controller;
//
//import com.azure.storage.blob.BlobClient;
//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobServiceClient;
//import com.fwl.unmannedstore.model.Product;
//
//import com.fwl.unmannedstore.security.authentication.JwtService;
//import com.fwl.unmannedstore.security.config.WebSecurityConfiguration;
//import com.fwl.unmannedstore.security.entity.Role;
//import com.fwl.unmannedstore.security.entity.User;
//import com.fwl.unmannedstore.service.ProductService;
//
//import jakarta.persistence.EntityManager;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.FilterType;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.sql.Timestamp;
//
//
//
//@ActiveProfiles("no-security")
//@WebMvcTest(value = ProductController.class, excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfiguration.class)
//})
//
//class ProductControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private ProductService productService;
//
//    @MockBean
//    private EntityManager entityManager;
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
//    @MockBean
//    private JwtService jwtService;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    private Product product;
//    private User user;
//
////    private String jwtToken;
//
//    @BeforeEach
//    void beforeEach() {
//        user = User.builder()
//                .id(1)
//                .email("marylam@gmail.com")
//                .role(Role.ROLE_ADMIN)
//                .password(passwordEncoder.encode("marylam@gmail.com"))
//                .firstname("Mary")
//                .lastname("Lam")
//                .profile("profile.jpg")
//                .build();
//        product = Product.builder()
//                .name("Frame Stand")
//                .category("Home Decorations")
//                .price(12)
//                .description("Lovely stand")
//                .isActive(true)
//                .prodId(1)
//                .creation_date(new Timestamp(System.currentTimeMillis()))
//                .updated_by_user(user).build();
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//
//
//    }
//
//    @Test
//    @WithMockUser(username = "marylam@gmail.com", roles = {"ROLE_ADMIN"})
//    public void addProduct() throws Exception {
////        jwtToken = jwtService.generateTokenFromUsername(user.getEmail(), Role.ROLE_ADMIN);
////        System.out.println("JWT Token: " + jwtToken);
//
//        Product inputProduct = Product.builder()
//                .name("Frame Stand")
//                .category("Home Decorations")
//                .price(12)
//                .description("Lovely stand")
//                .isActive(true)
//                .creation_date(new Timestamp(System.currentTimeMillis()))
//                .updated_by_user(user).build();
//
//        // save method is void --> return nothing
//        Mockito.doNothing().when(productService).save(inputProduct);
//
//        MockMultipartFile photoFile = new MockMultipartFile(
//                "photoFile",
//                "photo.jpg",
//                "image/jpeg",
//                "some-image-content".getBytes() // just example content
//        );
//
//        MockMultipartFile[] photoFiles = {
//                new MockMultipartFile(
//                        "photoFiles",
//                        "photo1.jpg",
//                        "image/jpeg",
//                        "some-image-content".getBytes()
//                ),
//                new MockMultipartFile(
//                        "photoFiles",
//                        "photo2.jpg",
//                        "image/jpeg",
//                        "some-image-content".getBytes()
//                )
//        };
//
//        MvcResult result = mockMvc.perform(
//                        MockMvcRequestBuilders.multipart("/save_product")
//                        .file(photoFile)
//                        .file(photoFiles[0])
//                        .file(photoFiles[1])
//                        .flashAttr("product", product)
//                        .contentType(MediaType.MULTIPART_FORM_DATA)
////                        .header("Authorization", "Bearer " + jwtToken)
//                        )
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/usms/products"))
//                .andReturn();
//
//        Mockito.verify(productService, Mockito.times(1)).save(inputProduct);
//
//        System.out.println("Result: " + result.getResponse().getContentAsString());
//
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