
package com.fwl.unmannedstore.respository;

import com.fwl.unmannedstore.controller.requestResponse.SalesDisplay;
import com.fwl.unmannedstore.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SalesRecordRepositoryTest {

    @Autowired
    private SalesRecordRepository salesRecordRepo;

    @Autowired
    private TestEntityManager testEntityManager;

    private Product product;
    private Store store1;
    private Store store2;
    private SalesRecord salesRecord;
    private Payment payment;
    private Cart cart;
    private Date startDate;
    private Date endDate;
    private Calendar cal;
    @BeforeEach
    void setUp() {
        Date date= new Date();
        cal = Calendar.getInstance();
        cal.setTime(date);
        startDate = date;
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

        payment = Payment.builder().paymentIntentId("pi_3NgdPTCeGEiTFd6o5Y7Inftk")
                .isSuccessful(true)
                .build();
        testEntityManager.persist(payment);

        List<RFID> rfidList = new ArrayList<>();
        rfidList.add(rfid1);
        cart = Cart.builder().rfidList(rfidList).build();
        testEntityManager.persist(cart);

        salesRecord = SalesRecord.builder()
                .store(store1).payment(payment).amountInPence(22).cart(cart)
                .transactionDateTime(payment.getPay_date_time()).build();
        testEntityManager.persist(salesRecord);
        payment.setSalesRecord(salesRecord);
        endDate = new Date();
    }

    @Test
    void findByStore() {
        SalesRecord salesRecord1 = salesRecordRepo.findByStore(store1).get(0);
        assertEquals(22, salesRecord1.getAmountInPence());
    }

//    @Test
//    void findByPeriod() {
//        SalesRecord salesRecord1 = salesRecordRepo.findByPeriod(startDate, endDate, store1.getStoreId()).get(0);
//        assertEquals(22, salesRecord1.getAmountInPence());
//    }

    @Test
    void findSumSalesByMonthAndYear() {
        List<SalesRecord> salesRecordList = salesRecordRepo.findAll();
        double sumSales = 0;
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        for(SalesRecord sales : salesRecordList) {
            Timestamp date = sales.getTransactionDateTime();
            if (date.getYear() + 1900 == year && date.getMonth() + 1 == month) {
                sumSales += sales.getAmountInPence();
            }
        }
        List<SalesDisplay> salesDisplays = salesRecordRepo.findSumSalesByMonthAndYear();
        int lastIndex = salesDisplays.size();
        // Get a default value
        SalesDisplay salesDisplay = salesDisplays.get(lastIndex - 1);
        for(SalesDisplay sales : salesDisplays) {
            if (sales.getYear() + 1900 == year && sales.getMonth() + 1 == month) {
                salesDisplay = sales;
                break;
            }
        }
        System.out.println("Sum of Sales" + sumSales);
        assertEquals((int) sumSales / 100 , (int) salesDisplay.getTotalValue());
    }

    @Test
    void findSumSalesByAMonth() {
        List<SalesRecord> salesRecordList = salesRecordRepo.findAll();
        double sumSales = 0;
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        System.out.println("Cal Date: " + year + " " + month + " ");
        for(SalesRecord sales : salesRecordList) {
            Timestamp date = sales.getTransactionDateTime();
            System.out.println("Date" + date.getYear() + " " + date.getMonth()+ " " +sales.getAmountInPence());
            if (date.getYear() + 1900 == year && date.getMonth() + 1 == month) {
                sumSales += sales.getAmountInPence();
            }
        }
        double sales = salesRecordRepo.findSumSalesByAMonth(year, month).get();
        System.out.println("Sum of Sales" + sumSales);
        assertEquals((int) sumSales / 100 , (int) sales);
    }
}