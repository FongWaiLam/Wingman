package com.fwl.unmannedstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
//@IdClass(CartId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart{
    @Id
    private int cartId; // primary key
    @OneToMany
    private List<RFID> rfidList;
    private double amount;

    public void addRFID(RFID rfid) {
        if (rfidList == null) {
            rfidList = new ArrayList<RFID>();
        }
        rfidList.add(rfid);
    }

}
