package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Business;
import com.fwl.unmannedstore.respository.BusinessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;

    // Add a new store or Update an existing store
    public void save(Business business) {
        businessRepository.save(business);
    }
}
