package com.fwl.unmannedstore.controller;

import com.fwl.unmannedstore.model.Store;
import com.fwl.unmannedstore.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckoutControllerTest {

    @MockBean
    private StoreService storeService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCheckoutView() throws Exception {
        List<Store> mockStoreList = new ArrayList<>();
        mockStoreList.add(new Store());

        when(storeService.getAllStores()).thenReturn(mockStoreList);

        mockMvc.perform(get("/checkout"))
                .andExpect(status().isOk())
                .andExpect(view().name("checkout_store"))
                .andExpect(model().attributeExists("storeList"));
    }

    @Test
    public void testCheckoutWithStoreId() throws Exception {
        int mockStoreId = 1;

        mockMvc.perform(get("/checkout/{storeId}", mockStoreId))
                .andExpect(status().isOk())
                .andExpect(view().name("checkout"));
    }
}
