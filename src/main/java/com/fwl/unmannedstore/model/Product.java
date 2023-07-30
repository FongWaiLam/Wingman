package com.fwl.unmannedstore.model;

import com.fwl.unmannedstore.security.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@ToString(exclude = "rfidList")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int prodId; // primary key
    @Column(
            nullable = false
    )
    private String name;
    private double price;
    private String description;
    private String category;
    private String photo; // path to image
    private List<String> photos;
    private int quantity = 0;
    private Timestamp creation_date;
    private Timestamp last_updated;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "updated_by_username",
            referencedColumnName = "email"
    )
    private User updated_by_user;

    @Column(
            nullable = false
    )
    private boolean isActive;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
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

//    public void addRFID(RFID rfid) {
//        if (rfidList == null) {
//            rfidList = new ArrayList<RFID>();
//        }
//        rfidList.add(rfid);
//    }
        public void addPhotos(String photo) {
        if (photos == null) {
            photos = new ArrayList<String>();
        }
        photos.add(photo);
    }

}
