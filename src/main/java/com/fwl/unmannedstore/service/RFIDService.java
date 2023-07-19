package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.respository.ProductRepository;
import com.fwl.unmannedstore.respository.RFIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RFIDService {
    @Autowired
    private RFIDRepository rfidRepository;
    @Autowired
    private ProductRepository productRepository;

    // Get the full RFID List
    public List<RFID> getAllRFIDs() {
        return rfidRepository.findAll();
    }

     // Get the full RFID List for a Product
    public List<RFID> getAllRFIDs(Product product) {
        return rfidRepository.findByProduct(product);
    }

    // Get the full RFID List for a Product
    public List<RFID> getAllRFIDs(Product product, Store store) {
        return rfidRepository.findByProductAndStore(product, store);
    }

    // Get a specific rfid (For update)
    public RFID getRFIDByEPC(String epc) {
        return rfidRepository.findById(epc).get();
    }

    // Add a new RFID or Update an existing RFID
    public void save(RFID rfid) {
        rfidRepository.save(rfid);
    }

    // Sold a product
    public void sold(String epc) {
        RFID rfid = rfidRepository.findById(epc).get();
        sold(rfid);
    }

    // Sold a product
    public void sold(RFID rfid) {
        rfid.setSold(true);
        save(rfid);

        Product product = rfid.getProduct();
        quantityReduceByOne(product);
        productRepository.save(product);
    }

    // Delete a RFID
    public void deleteByEPC(String epc) {
        RFID rfid = getRFIDByEPC(epc);
        quantityReduceByOne(rfid.getProduct());

        rfidRepository.deleteById(epc);
    }

    // Helper method: decrease the quantity of the product by 1
    private void quantityReduceByOne(Product product) {
        product.setQuantity(product.getQuantity() - 1);
    }

}
