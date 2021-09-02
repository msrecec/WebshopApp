package com.example.webshop.rest;

import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.service.product.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        this.productDTO = ProductDTO.builder()
                .code("test")
                .isAvailable(true)
                .name("test")
                .description("test")
                .priceHrk(new BigDecimal(100))
                .build();
    }


    @Test
    void getAllProductsExistsTest() throws Exception {

        // given

        when(productService.findAll()).thenReturn(Arrays.asList(productDTO));

        // when

        // then

        this.mockMvc.perform(
                        get("/api/v1/product")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

    }


    @Test
    void getAllProductsNotExistsTest() throws Exception {

        // given

        when(productService.findAll()).thenReturn(new ArrayList<>());

        // when

        // then

        this.mockMvc.perform(
                        get("/api/v1/product")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    void getProductByCodeExistsTest() throws Exception {
        when(productService.findByCode("test")).thenReturn(Optional.of(productDTO));

        this.mockMvc.perform(
                get("/api/v1/product/code/{code}", "test")
        )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("test"))
                .andExpect(jsonPath("$.isAvailable").value(true))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.description").value("test"))
                .andExpect(jsonPath("$.priceHrk").value(new BigDecimal(100)));

    }


    @Test
    void getProductByCodeNotExistsTest() throws Exception {
        when(productService.findByCode("test")).thenReturn(Optional.empty());

        this.mockMvc.perform(
                        get("/api/v1/product/code/{code}", "test")
                )
                .andExpect(status().isNotFound());

    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}