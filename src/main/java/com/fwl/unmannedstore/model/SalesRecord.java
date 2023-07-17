package com.fwl.unmannedstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesRecord {
    @Id
    private int rfid;
    @OneToOne
    @JoinColumn(
            name = "pay_id",
            referencedColumnName = "pay_id"
    )
    private Payment payment;
    @ManyToOne
    @JoinColumn(
            name = "store_id",
            referencedColumnName = "store_id"
    )
    private Store store;
    private Timestamp transaction_date_time;
    private double amount;
    private String status;

    @PrePersist
    void createdAt() {
        this.transaction_date_time = new Timestamp(System.currentTimeMillis());
    }
}
