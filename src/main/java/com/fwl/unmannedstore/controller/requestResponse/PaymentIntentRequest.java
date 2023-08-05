package com.fwl.unmannedstore.controller.requestResponse;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentIntentRequest {
    private String paymentIntentId;
    private long amount;
}
