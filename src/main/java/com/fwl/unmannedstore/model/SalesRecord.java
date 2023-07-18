package com.fwl.unmannedstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesRecord {
    @Id
    private int salesId;
    @OneToOne
    @JoinColumn(
            name = "payId",
            referencedColumnName = "payId"
    )
    private Payment payment;
    @ManyToOne
    @JoinColumn(
            name = "storeId",
            referencedColumnName = "storeId"
    )
    private Store store;
    private Timestamp transaction_date_time;
    private double amount;
    private String status;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "cartId",
            referencedColumnName = "cartId"
    )
    private Cart cart;

    @PrePersist
    void createdAt() {
        this.transaction_date_time = new Timestamp(System.currentTimeMillis());
    }
}
