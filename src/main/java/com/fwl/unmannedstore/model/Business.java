package com.fwl.unmannedstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Business {
    @Id
    private String name; // primary key, max 255 chars
    private String profile; // max 255 chars
    private String logo; // path to image
}
