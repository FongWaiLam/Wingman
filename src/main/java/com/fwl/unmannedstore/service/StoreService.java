package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.respository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    // Get the full store List
    public List<Store> getAllRFIDs() {
        return storeRepository.findAll();
    }

    // Get a specific store By ID
    public Store getStoreById(int storeId) {
        return storeRepository.findById(storeId).get();
    }


    // Add a new store or Update an existing store
    public void save(Store store) {
        storeRepository.save(store);
    }
}
