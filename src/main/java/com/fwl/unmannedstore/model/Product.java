package com.fwl.unmannedstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prod_id; // primary key
    @Column(
            nullable = false
    )
    private String name;
    private String description; // max 255 chars
    private double price;
    private Timestamp creation_date;
    private Timestamp last_updated;
    @ManyToOne
    @JoinColumn(
            name = "updated_by_username",
            referencedColumnName = "username"
    )
    private User updated_by_user;
    @Embedded
    private Category category;
    @Column(
            nullable = false
    )
    private boolean isActive;
    private String photo; // path to image
    @OneToMany(
            mappedBy = "product"
    )
    private List<RFID> rfidList;

    @PrePersist
    void createdAt() {
        this.creation_date = this.last_updated = new Timestamp(System.currentTimeMillis());
//        Add after the Security is implemented
//        this.updated_by_user = LoggedUser.get();
    }

    @PreUpdate
    void updatedAt() {
        this.last_updated = new Timestamp(System.currentTimeMillis());
        //Add after the Security is implemented
//        this.updated_by_user = LoggedUser.get();
    }

    public void addRFID(RFID rfid) {
        if (rfidList == null) {
            rfidList = new ArrayList<RFID>();
        }
        rfidList.add(rfid);
    }

}
