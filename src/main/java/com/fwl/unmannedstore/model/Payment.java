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
//@Table(name = "payment", indexes = {@Index(name = "payment_intent_id_index", columnList = "paymentIntentId")})
public class Payment {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int payId;
//    @Column(unique = true, nullable = false)
    private String paymentIntentId; // primary key Assigned by Stripe
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
