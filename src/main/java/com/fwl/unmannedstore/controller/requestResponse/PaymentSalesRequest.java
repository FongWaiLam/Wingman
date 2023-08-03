package com.fwl.unmannedstore.controller.requestResponse;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSalesRequest {
    @NotBlank
    private List<String> epcList;
    @NotBlank
    private double amount; // total
    private String paymentIntentId;
    @NotBlank
    private boolean isSuccessful;
    @NotBlank
    private int storeId;
}
