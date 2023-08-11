package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RFIDRepositoryTest {
    @Autowired
    private RFIDRepository rfidRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    // @Test
    // public void printRFIDFullList() {

    //     System.out.println(rfidRepository.findAll());
    // }

    // @Test
    // public void printRFIDByProduct() {
    //     Product firstProduct = productRepository.findById(1).get();
    //     List<RFID> rfidList = rfidRepository.findByProduct(firstProduct);
    //     System.out.println(rfidList);
    // }

    // @Test
    // public void addRFIDtoExistingProduct(){
    //     Store glasgowStore = storeRepository.findById(1).get();
    //     Product abBloodTypeCup = productRepository.findById(2).get();
    //     RFID abBloodTypeCupRFID2 = RFID.builder()
    //             .epc("e28011606000020ea907f4af").product(abBloodTypeCup).store(glasgowStore).build();
    //     rfidRepository.save(abBloodTypeCupRFID2);
    // }

    // @Test
    // public void printRFIDBasedOnProductAndStore() {
    //     Store glasgowStore = storeRepository.findById(1).get();
    //     Product abBloodTypeCup = productRepository.findById(2).get();
    //     System.out.println(rfidRepository.findByProductAndStore(abBloodTypeCup, glasgowStore));
    // }
}
