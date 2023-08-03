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
public class AddRFIDRequest {
    private List<String> epcList;
    private int prodId;
    private String name;
    private int storeId;
}