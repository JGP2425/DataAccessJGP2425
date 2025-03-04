package com.jgp.OnlineMarket.OnlineMarket.controllers;

import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.services.sellersService;
import com.jgp.OnlineMarket.OnlineMarket.services.productService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityNotFoundException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class SellersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private sellersService sellersService;

    @Mock
    private productService productService;

    @InjectMocks
    private sellersController sellersController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new sellersController(sellersService, productService)).build();
    }
    @Test
    void testGetSellerByCifOK() throws Exception {
        SellerEntity seller = new SellerEntity();
        seller.setCif("D45678901");
        seller.setName("HomeEssentials");

        when(sellersService.findSellerByCif("D45678901")).thenReturn(seller);

        mockMvc = MockMvcBuilders.standaloneSetup(sellersController).build();

        mockMvc.perform(get("/sellers/cif/D45678901"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HomeEssentials"));

        verify(sellersService, times(1)).findSellerByCif("D45678901");
    }

    @Test
    void testGetSellerByCifNotFound() throws Exception {
        when(sellersService.findSellerByCif("errorCif")).thenReturn(null);

        mockMvc = MockMvcBuilders.standaloneSetup(sellersController).build();

        mockMvc.perform(get("/sellers/cif/errorCif"))
                .andExpect(status().isNotFound());

        verify(sellersService, times(1)).findSellerByCif("errorCif");
    }

    @Test
    void testUpdateSellerOk() throws Exception {
        SellerEntity seller = new SellerEntity();
        seller.setCif("D45678901");
        seller.setName("new HomeEssentials");

        when(sellersService.updateSeller(any(SellerEntity.class))).thenReturn(seller);

        mockMvc.perform(post("/sellers/updateSeller")
                        .param("cif", "D45678901")
                        .param("name", "new HomeEssentials")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/view/index"));

        verify(sellersService, times(1)).updateSeller(any(SellerEntity.class));
    }

    @Test
    void testUpdateSellerInvalidData() throws Exception {
        SellerEntity seller = new SellerEntity();
        seller.setCif("D45678901");
        seller.setName("");

        when(sellersService.updateSeller(any(SellerEntity.class)))
                .thenThrow(new IllegalArgumentException("Invalid seller data"));

        mockMvc.perform(post("/sellers/updateSeller")
                        .param("cif", "D45678901")
                        .param("name", "")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest());

        verify(sellersService, times(1)).updateSeller(any(SellerEntity.class));
    }

    @Test
    void testUpdateSellerNotFound() throws Exception {
        SellerEntity seller = new SellerEntity();
        seller.setCif("D45678901");
        seller.setName("NonExistentSeller");

        when(sellersService.updateSeller(any(SellerEntity.class)))
                .thenThrow(new EntityNotFoundException("Seller not found"));

        mockMvc.perform(post("/sellers/updateSeller")
                        .param("cif", "D45678901")
                        .param("name", "NonExistentSeller")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isNotFound());

        verify(sellersService, times(1)).updateSeller(any(SellerEntity.class));
    }



}
