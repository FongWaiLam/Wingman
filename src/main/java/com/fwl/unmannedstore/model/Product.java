package com.fwl.unmannedstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prod_id; // primary key
    private String name;
    private String description; // max 255 chars
    private double price;
    private Timestamp creation_date;
    private Timestamp last_updated;
    private String updated_by_username;
    private int cat_id;
    private boolean isActive;
    private String photo; // path to image
}
