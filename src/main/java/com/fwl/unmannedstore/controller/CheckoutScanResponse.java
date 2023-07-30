package com.fwl.unmannedstore.controller;

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

}
