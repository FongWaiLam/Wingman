package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Payment;
import com.fwl.unmannedstore.model.SalesRecord;
import com.fwl.unmannedstore.respository.SalesRecordRepository;
import org.springframework.stereotype.Service;

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

    // Add a new sales record or Update an existing sales record
    public void save(SalesRecord salesRecord) {
        salesRecordRepository.save(salesRecord);
    }

    // Delete a sales record
    public void deleteById(int salesId) {
        salesRecordRepository.deleteById(salesId);
    }
}
