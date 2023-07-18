package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.SalesRecord;
import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.respository.SalesRecordRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalesRecordService {
    private SalesRecordRepository salesRecordRepository;

    // Get the full Sales Record List
    public List<SalesRecord> getAllSalesRecord() {
        return salesRecordRepository.findAll();
    }

    // Get a specific sales record (For update)
    public SalesRecord getSalesRecordById(int salesId) {
        return salesRecordRepository.findById(salesId).get();
    }

    // Get the full sales record for a store
    public List<SalesRecord> getSalesRecordById(Store store) {
        return salesRecordRepository.findByStore(store);
    }

    // Get the full sales record for a store for a period of time
    public List<SalesRecord> getSalesRecordByPeriod(Date start, Date end, Store store) {
        return salesRecordRepository.findByPeriod(start, end, store.getStoreId());
    }

    // Add a new sales record or Update an existing sales record
    public void save(SalesRecord salesRecord) {
        salesRecordRepository.save(salesRecord);
    }

    // Delete a sales record
    public void deleteById(int salesId) {
        salesRecordRepository.deleteById(salesId);
    }
}
