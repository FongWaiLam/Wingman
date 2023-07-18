package com.fwl.unmannedstore.service;

import com.fwl.unmannedstore.model.Payment;
import com.fwl.unmannedstore.respository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;

    // Get the full payment List
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // Get a specific payment (For update)
    public Payment getPaymentById(int payId) {
        return paymentRepository.findById(payId).get();
    }

    // Add a new payment or Update an existing payment
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    // Delete a payment
    public void deleteById(int payId) {
        paymentRepository.deleteById(payId);
    }
}
