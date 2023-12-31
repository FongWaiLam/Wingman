package com.fwl.unmannedstore.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "salesRecordList")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int storeId;   // primary key
    private String name;
    private String address;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date established_date;
    private String status;
    @OneToMany(
            mappedBy = "store"
    )
    private List<SalesRecord> salesRecordList;
    public void addSalesRecord(SalesRecord salesRecord) {
        if (salesRecordList == null) {
            salesRecordList = new ArrayList<SalesRecord>();
        }
        salesRecordList.add(salesRecord);
    }
}
