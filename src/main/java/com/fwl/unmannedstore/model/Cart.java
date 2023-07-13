package com.fwl.unmannedstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@IdClass(CartId.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart implements Serializable {
    @Id
    private int cart_id; // primary key
    @Id
    private int prod_id; // primary key
    @Id
    private int rfid; // primary key
}
