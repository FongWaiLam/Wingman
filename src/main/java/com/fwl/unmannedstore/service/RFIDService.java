package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.respository.RFIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RFIDService {
    @Autowired
    private RFIDRepository rfidRepository;

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

    // Add a new RFID or Update an existing RFID
    public void save(RFID rfid) {
        rfidRepository.save(rfid);
    }

    // Sold a product
    public void sold(int epc) {
        rfidRepository.findById(epc).get().setSold(true);
    }

    // Sold a product
    public void sold(RFID rfid) {
        rfid.setSold(true);
    }

    // Delete a RFID
    public void deleteById(int epc) {
        rfidRepository.deleteById(epc);
    }

}
