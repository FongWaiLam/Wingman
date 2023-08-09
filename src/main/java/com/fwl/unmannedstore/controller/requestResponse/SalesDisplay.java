package com.fwl.unmannedstore.controller.requestResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SalesDisplay {
    private int year;
    private int month;
    private double totalValue;
}
