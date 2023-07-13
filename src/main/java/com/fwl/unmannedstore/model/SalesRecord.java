package com.fwl.unmannedstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesRecord {
    @Id
    private int rfid;
    private int pay_id;
    private int store_id;
    private Timestamp purchased_date_time;
    private double amount;
    private String status;  // Max 50 chars
}
