package com.fwl.unmannedstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
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
public class Payment {
    @Id
    private int payId; // primary key
    private Timestamp pay_date_time;
    @OneToOne(
            mappedBy = "payment"
    )
    private SalesRecord salesRecord;
    private boolean isSuccessful = false;
    @PrePersist
    void createdAt() {
        this.pay_date_time = new Timestamp(System.currentTimeMillis());
    }

}
