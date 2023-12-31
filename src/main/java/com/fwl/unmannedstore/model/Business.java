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
public class Business {
    @Id
    private String name; // primary key, max 255 chars
    private String profile; // max 255 chars
    private String logo; // path to image
}
