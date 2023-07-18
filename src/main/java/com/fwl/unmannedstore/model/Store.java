package com.fwl.unmannedstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storeId;   // primary key
    private String name;
    private String address;
    private Date established_date;
    private String status = "normal";
//    @OneToMany(
//            mappedBy = "store"
//    )
//    private List<SalesRecord> salesRecordList;
//    public void addSalesRecord(SalesRecord salesRecord) {
//        if (salesRecordList == null) {
//            salesRecordList = new ArrayList<SalesRecord>();
//        }
//        salesRecordList.add(salesRecord);
//    }
}
