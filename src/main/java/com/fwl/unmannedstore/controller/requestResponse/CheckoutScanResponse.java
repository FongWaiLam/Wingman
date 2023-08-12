package com.fwl.unmannedstore.controller.requestResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutScanResponse {
    private String epc;
    private int prodId;
    private String name;
    private double price;
    private String photo;
    private String conflictMessage;

    public CheckoutScanResponse(String epc, int prodId, String name, double price, String photo) {
        this.epc = epc;
        this.prodId = prodId;
        this.name = name;
        this.price = price;
        this.photo = photo;
    }
}
