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
public class RFID {
    @Id
    private String epc;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "prodId",
            referencedColumnName = "prodId",
            nullable = false
    )
    private Product product;
    private boolean isSold = false;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "storeId",
            referencedColumnName = "storeId",
            nullable = false
    )
    private Store store;

    @PrePersist
    void createdAt() {
        this.product.setQuantity(product.getQuantity() + 1);
    }

}
