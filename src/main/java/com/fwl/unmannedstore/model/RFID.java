package com.fwl.unmannedstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RFID {
    @Id
    private int rfid;
    @ManyToOne
    @JoinColumn(
            name = "prod_id",
            referencedColumnName = "prod_id",
            nullable = false
    )
    private Product product;
    private boolean isSold = false;
}
