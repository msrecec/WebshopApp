package com.example.webshop.rest;

import com.example.webshop.command.orderItem.OrderItemPostCommand;
import com.example.webshop.command.orderItem.OrderItemPutCommand;
import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.service.orderItem.OrderItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderItemControllerTest {

    @MockBean
    OrderItemService orderItemService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    OrderItemPostCommand orderItemPostCommand;
    OrderItemPutCommand orderItemPutCommand;
    OrderItemDTO orderItemDTO;

    @BeforeEach
    void setUp() {
        orderItemPostCommand = OrderItemPostCommand.builder().orderId(1L).code("test").quantity(1).build();
        orderItemPutCommand = OrderItemPutCommand.builder().id(1L).code("test").quantity(1).build();
        orderItemDTO = OrderItemDTO.builder()
                .id(1L)
                .quantity(1)
                .product(ProductDTO.builder()
                        .code("1234567890")
                        .name("test")
                        .description("test")
                        .isAvailable(true)
                        .priceHrk(new BigDecimal(100))
                        .build()).build();
    }

    @Test
    void findAllTest() throws Exception {

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();

        orderItemDTOList.add(orderItemDTO);

        // given

        when(orderItemService.findAll()).thenReturn(orderItemDTOList);

        // when

        // then

        this.mockMvc.perform(
                        get("/api/v1/orderItem")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }
}