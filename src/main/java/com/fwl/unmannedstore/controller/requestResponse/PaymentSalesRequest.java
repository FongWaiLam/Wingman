package com.fwl.unmannedstore.controller.requestResponse;

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

    private List<String> epcList;

    private long amountInPence; // total

    private String paymentIntentId;

    private boolean isSuccessful;

    private int storeId;

}
