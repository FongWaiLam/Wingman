//package com.fwl.unmannedstore.respository;
//
//import com.fwl.unmannedstore.model.Product;
//import com.fwl.unmannedstore.model.RFID;
//import com.fwl.unmannedstore.model.Store;
//import com.fwl.unmannedstore.security.repo.UserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Date;
//
//@SpringBootTest
//class RepositoryIntegrationTest {
//
//    @Autowired
//    private RFIDRepository rfidRepository;
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private StoreRepository storeRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void addRFIDOfProduct() {
//        User admin = User.builder().username("admin001").password("ad20230717").build();
//        userRepository.save(admin);
//        Product phoneCase = Product.builder()
//                .name("Earth iPhone Plastic Phone Case")
//                .description("•\tCOMPATIBILITY - iPhone 13 Pro Max\n" +
//                        "•\tUltra-PROTECTION – Air-bubble absorbing technology\n" +
//                        "•\tBEST SHAPE EVER – Thin and fit to size\n" +
//                        "•\tONE-YEAR WARRANTY – Case durability and warranty\n")
//                .price(34.99)
//                .category("Gadget accessories")
//                .updated_by_user(admin)
//                .build();
//        productRepository.save(phoneCase);
//        Store glasgowStore = Store.builder()
//                .name("Driftwood Habitat")
//                .address("10B Byres Rd, Glasgow G12 8TD")
//                .established_date(new Date())
//                .build();
//        storeRepository.save(glasgowStore);
//        RFID phoneCaseRFID = RFID.builder()
//                .epc("e28011606000020ea907c89f").product(phoneCase).store(glasgowStore).build();
//        rfidRepository.save(phoneCaseRFID);
//    }
//    @Test
//    public void add2ndRFIDOfProduct() {
//        User admin = userRepository.findByUsername("admin001");
//        Product abBloodTypeCup = Product.builder()
//                .name("BloodType AB Cup for You")
//                .description("•\tFeatured AB text with a pale colour background\n" +
//                        "•\t500ml Capacity\n" +
//                        "•\tDishwasher and microwave safe\n")
//                .price(19.99)
//                .category("Kitchen & Dining")
//                .updated_by_user(admin)
//                .build();
//        productRepository.save(abBloodTypeCup);
//        Store glasgowStore = storeRepository.findById(1).get();
//        RFID abBloodTypeCupRFID = RFID.builder()
//                .epc("e28011606000020ea907c8af").product(abBloodTypeCup).store(glasgowStore).build();
//        rfidRepository.save(abBloodTypeCupRFID);
//    }
//
//
//}