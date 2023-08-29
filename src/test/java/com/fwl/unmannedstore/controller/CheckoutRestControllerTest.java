package com.fwl.unmannedstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fwl.unmannedstore.controller.requestResponse.CheckoutScanRequest;
import com.fwl.unmannedstore.model.Product;
import com.fwl.unmannedstore.model.RFID;
import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.service.RFIDService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckoutRestControllerTest {

    @MockBean
    private RFIDService rfidService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetProductSuccess() throws Exception {
        String mockEpc = "mockEpc";
        Product mockProduct = new Product();
        mockProduct.setProdId(1);
        mockProduct.setName("Portable Wireless Keyboard");
        mockProduct.setPrice(10.0);
        mockProduct.setPhoto("portable_keyboard.jpg");

        RFID mockRFID = new RFID();
        mockRFID.setEpc("e2004205f760641601f6f266");
        mockRFID.setStore(new Store());
        mockRFID.setSold(false);
        mockRFID.setProduct(mockProduct);

        CheckoutScanRequest request = new CheckoutScanRequest();
        request.setEpc(mockEpc);

        when(rfidService.getRFIDByEPC(mockEpc)).thenReturn(mockRFID);

        mockMvc.perform(post("/checkout/get_product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.epc").value(mockEpc))
                .andExpect(jsonPath("$.prodId").value(mockProduct.getProdId()))
                .andExpect(jsonPath("$.name").value(mockProduct.getName()))
                .andExpect(jsonPath("$.price").value(mockProduct.getPrice()))
                .andExpect(jsonPath("$.photo").value(mockProduct.getPhoto()));
    }

    @Test
    public void testGetProductConflict() throws Exception {
        String mockEpc = "mockEpc";

        RFID mockRFID = new RFID();
        mockRFID.setSold(true);

        CheckoutScanRequest request = new CheckoutScanRequest();
        request.setEpc(mockEpc);

        when(rfidService.getRFIDByEPC(mockEpc)).thenReturn(mockRFID);

        mockMvc.perform(post("/checkout/get_product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.conflictMessage").value("Product already sold!"));
    }
}
