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
    private int salesId;
    @OneToOne
    @JoinColumn(
            name = "paymentIntentId",
            referencedColumnName = "paymentIntentId"
    )
    private Payment payment;
    @ManyToOne
    @JoinColumn(
            name = "storeId",
            referencedColumnName = "storeId"
    )
    private Store store;
    private Timestamp transactionDateTime;
    private long amount;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "cartId",
            referencedColumnName = "cartId"
    )
    private Cart cart;

//    @PrePersist
//    void createdAt() {
//        this.transactionDateTime = new Timestamp(System.currentTimeMillis());
//    }
}
