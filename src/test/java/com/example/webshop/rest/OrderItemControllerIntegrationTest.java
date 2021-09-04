package com.example.webshop.rest;


import com.example.webshop.command.orderItem.OrderItemPostCommand;
import com.example.webshop.command.orderItem.OrderItemPutCommand;
import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.service.orderItem.OrderItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderItemControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    OrderItemPostCommand orderItemPostCommand;
    OrderItemPutCommand orderItemPutCommand;

    @BeforeEach
    void setUp() {
        orderItemPostCommand = OrderItemPostCommand.builder().orderId(1L).code("1234567899").quantity(2).build();
        orderItemPutCommand = OrderItemPutCommand.builder().id(1L).code("1234567890").quantity(2).build();
    }


    @Test
    void findAllExistsIT() throws Exception {

        // given

        // when

        // then

        this.mockMvc.perform(
                        get("/api/v1/orderItem")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }


    @Test
    void findByIdExistsIT() throws Exception {

        this.mockMvc.perform(
                        get("/api/v1/orderItem/id/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.product.code").value("1234567890"))
                .andExpect(jsonPath("$.product.isAvailable").value(true))
                .andExpect(jsonPath("$.product.name").value("Great product"))
                .andExpect(jsonPath("$.product.description").value("This is a great product"));

    }


    @Test
    void updateExistsIT() throws Exception {

        this.mockMvc.perform(
                        put("/api/v1/orderItem")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderItemPutCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.product.code").value("1234567890"))
                .andExpect(jsonPath("$.product.isAvailable").value(true))
                .andExpect(jsonPath("$.product.name").value("Great product"))
                .andExpect(jsonPath("$.product.description").value("This is a great product"));

    }

    @Test
    void saveExistsIT() throws Exception {

        // given
        this.mockMvc.perform(
                        MockMvcRequestBuilders.delete("/api/v1/orderItem/id/{id}", 2L)
                )
                .andExpect(status().isNoContent());

        // when

        // then

        this.mockMvc.perform(
                        post("/api/v1/orderItem")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderItemPostCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.product.code").value("1234567899"))
                .andExpect(jsonPath("$.product.isAvailable").value(true))
                .andExpect(jsonPath("$.product.name").value("Another Great product"))
                .andExpect(jsonPath("$.product.description").value("This is an another great product"));

    }

    @Test
    void deleteByIdIT() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/orderItem/id/{id}", 1L)
        ).andExpect(status().isNoContent());

        this.mockMvc.perform(
                        get("/api/v1/orderItem")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

    }

}
