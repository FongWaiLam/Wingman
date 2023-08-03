package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.security.UserRepository;
import com.fwl.unmannedstore.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    // Get the full Product List/ Inventory List
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
