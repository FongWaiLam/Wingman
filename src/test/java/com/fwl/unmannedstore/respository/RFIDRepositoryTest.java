package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.controller.requestResponse.InventoryDisplay;
import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RFIDRepositoryTest {

    @Autowired
    private RFIDRepository rfidRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private Product product;
    private Store store1;
    private Store store2;

    @BeforeEach
    void setUp() {
        // Create a mock data
        product = Product.builder()
                .name("Luggage Cover")
                .price(22)
                .category("Travel")
                .description("Let's go travel!")
                .isActive(true)
                .photo("luggage_cover.jpg")
                .build();
        testEntityManager.persist(product);

        store1 = Store.builder()
                .name("University Avenue Store")
                .address("12 University Avenue")
                .status("Normal")
                .established_date(new Date(System.currentTimeMillis()))
                .build();
        testEntityManager.persist(store1);
        store2 = Store.builder()
                .name("London City Store")
                .address("33 Westminster Road")
                .status("Normal")
                .established_date(new Date(System.currentTimeMillis()))
                .build();
        testEntityManager.persist(store2);
        RFID rfid1 = RFID.builder()
                .epc("e2004206ab11641601f6fe24")
                .isSold(false)
                .product(product)
                .store(store1)
                .build();
        testEntityManager.persist(rfid1);
        RFID rfid2 = RFID.builder()
                .epc("e2004206ab11641601f6fe66")
                .isSold(false)
                .product(product)
                .store(store2)
                .build();
        testEntityManager.persist(rfid2);
        RFID rfid3 = RFID.builder()
                .epc("e2004206ab11641601f6fef6")
                .isSold(false)
                .product(product)
                .store(store2)
                .build();
        testEntityManager.persist(rfid3);
    }

    @Test
    void findByProduct() {
        List<RFID> rfidList = rfidRepository.findByProduct(product);
        RFID rfid = rfidList.get(0);
        assertEquals("e2004206ab11641601f6fe24", rfid.getEpc());
    }

    @Test
    void findByProductAndStore() {
        List<RFID> rfidList = rfidRepository.findByProductAndStore(product, store1);
        RFID rfid = rfidList.get(0);
        assertEquals("e2004206ab11641601f6fe24", rfid.getEpc());
        assertEquals(store1.getStoreId(), rfid.getStore().getStoreId());
    }

    @Test
    void findAllUnsoldProductTotalValueAndQuantity() {
        List<InventoryDisplay> inventoryDisplays = rfidRepository.findAllUnsoldProductTotalValueAndQuantity();
        InventoryDisplay inventoryDisplay = null;
        for (InventoryDisplay inventory: inventoryDisplays) {
            if (inventory.getProdName().equals("Luggage Cover") && inventory.getStoreId() == store1.getStoreId()) {
                inventoryDisplay = inventory;
                break;
            }
        }
        assertEquals("Luggage Cover", inventoryDisplay.getProdName());
        if (inventoryDisplay.getStoreId() == store1.getStoreId()) {
            List<RFID> rfidList = rfidRepository.findByProductAndStore(product, store1);

            int noOfProduct = 0;
            double totalValue = 0;
            for(RFID rfid: rfidList) {
                if (!rfid.isSold()) {
                    noOfProduct++;
                    totalValue += rfid.getProduct().getPrice();
                }
            }
            assertEquals(inventoryDisplay.getTotalAmount(), noOfProduct);
            assertEquals(inventoryDisplay.getTotalValue(), totalValue);
        }
    }

    @Test
    void findAllUnsoldProductTotalValueAndQuantityByCatIgnoreStore() {
        List<InventoryDisplay> inventoryDisplays = rfidRepository.findAllUnsoldProductTotalValueAndQuantityByCatIgnoreStore();
        InventoryDisplay inventoryDisplay = null;
        for (InventoryDisplay inventory: inventoryDisplays) {
            if (inventory.getCategory().equals("Travel")) {
                inventoryDisplay = inventory;
                break;
            }
        }
        assertEquals("Travel", inventoryDisplay.getCategory());

        List<RFID> rfidList = rfidRepository.findByProduct(product);

        int noOfProduct = 0;
        double totalValue = 0;
        for(RFID rfid: rfidList) {
            if (!rfid.isSold() && rfid.getProduct().getCategory().equals("Travel")) {
                noOfProduct++;
                totalValue += rfid.getProduct().getPrice();
            }
        }
        assertEquals(noOfProduct, inventoryDisplay.getTotalAmount());
        assertEquals(totalValue, inventoryDisplay.getTotalValue());
    }

    @Test
    void findAllUnsoldProductTotalValueAndQuantityByCat() {
        List<InventoryDisplay> inventoryDisplays = rfidRepository.findAllUnsoldProductTotalValueAndQuantityByCat(product.getCategory());
        InventoryDisplay inventoryDisplay = null;
        for (InventoryDisplay inventory: inventoryDisplays) {
            if (inventory.getProdName().equals("Luggage Cover")
                    && inventory.getCategory().equals(product.getCategory())
                    && inventory.getStoreId() == store2.getStoreId()) {
                inventoryDisplay = inventory;
                break;
            }
        }
        assertEquals("Luggage Cover", inventoryDisplay.getProdName());

        List<RFID> rfidList = rfidRepository.findByProduct(product);
        int noOfProductInStore2 = 0;
        double totalValue = 0;
        for (RFID rfid : rfidList) {
            if (rfid.getStore().getStoreId() == store2.getStoreId() && !rfid.isSold()) {
                noOfProductInStore2++;
                totalValue += rfid.getProduct().getPrice();
            }
        }
        assertEquals(noOfProductInStore2, inventoryDisplay.getTotalAmount());
        assertEquals(totalValue, inventoryDisplay.getTotalValue());
    }

    @Test
    void findAllUnsoldProductTotalValueAndQuantityByStore() {
        List<InventoryDisplay> inventoryDisplays = rfidRepository.findAllUnsoldProductTotalValueAndQuantityByStore();
        InventoryDisplay inventoryDisplay = null;
        for (InventoryDisplay inventory: inventoryDisplays) {
            if (inventory.getStoreId() == store1.getStoreId()) {
                inventoryDisplay = inventory;
                break;
            }
        }
        assertEquals("University Avenue Store", inventoryDisplay.getStoreName());

        List<RFID> rfidList = rfidRepository.findAll();
        int noOfProductInStore1 = 0;
        double totalValue = 0;
        for (RFID rfid : rfidList) {
            if (rfid.getStore().getStoreId() == store1.getStoreId() && !rfid.isSold()) {
                noOfProductInStore1++;
                totalValue += rfid.getProduct().getPrice();
            }
        }
        assertEquals(noOfProductInStore1, inventoryDisplay.getTotalAmount());
        assertEquals(totalValue, inventoryDisplay.getTotalValue());
    }

    @Test
    void findAllUnsoldProductTotalValueAndQuantityInAStore() {
        List<InventoryDisplay> inventoryDisplays = rfidRepository.findAllUnsoldProductTotalValueAndQuantityInAStore(store1.getStoreId());
        InventoryDisplay inventoryDisplay = null;
        for (InventoryDisplay inventory: inventoryDisplays) {
            if (inventory.getStoreId() == store1.getStoreId()) {
                inventoryDisplay = inventory;
                break;
            }
        }
        assertEquals("University Avenue Store", inventoryDisplay.getStoreName());

        List<RFID> rfidList = rfidRepository.findByProductAndStore(product, store1);
        int noOfProductInStore1 = 0;
        double totalValue = 0;
        for (RFID rfid : rfidList) {
            if (rfid.getStore().getStoreId() == store1.getStoreId() && !rfid.isSold()) {
                noOfProductInStore1++;
                totalValue += rfid.getProduct().getPrice();
            }
        }
        assertEquals(noOfProductInStore1, inventoryDisplay.getTotalAmount());
        assertEquals(totalValue, inventoryDisplay.getTotalValue());
    }
}