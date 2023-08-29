package com.fwl.unmannedstore.controller.requestResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDisplay {
    private int prodId;
    private String prodName;
    private String category;
    private long totalAmount;
    private double totalValue;
    private int storeId;
    private String storeName;

    public InventoryDisplay(int prodId, String prodName, String category, long totalAmount, double totalValue) {
        this.prodId = prodId;
        this.prodName = prodName;
        this.category = category;
        this.totalAmount = totalAmount;
        this.totalValue = totalValue;
    }

    public InventoryDisplay(long totalAmount, double totalValue, int storeId, String storeName) {
        this.totalAmount = totalAmount;
        this.totalValue = totalValue;
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public InventoryDisplay(String category, long totalAmount, double totalValue) {
        this.category = category;
        this.totalAmount = totalAmount;
        this.totalValue = totalValue;
    }
}
