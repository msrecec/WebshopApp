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


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


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
        orderItemPostCommand = OrderItemPostCommand.builder().orderId(1L).code("1234567890").quantity(1).build();
        orderItemPutCommand = OrderItemPutCommand.builder().id(1L).code("1234567890").quantity(1).build();
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
    void findAllExistsTest() throws Exception {

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
    void findAllNotExistsTest() throws Exception {

        // given

        when(orderItemService.findAll()).thenReturn(new ArrayList<>());

        // when

        // then

        this.mockMvc.perform(
                        get("/api/v1/orderItem")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }

    @Test
    void findByIdExistsTest() throws Exception {

        when(orderItemService.findById(1L)).thenReturn(Optional.of(orderItemDTO));

        this.mockMvc.perform(
                        get("/api/v1/orderItem/id/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.product.code").value("1234567890"))
                .andExpect(jsonPath("$.product.isAvailable").value(true))
                .andExpect(jsonPath("$.product.name").value("test"))
                .andExpect(jsonPath("$.product.description").value("test"))
                .andExpect(jsonPath("$.product.priceHrk").value(new BigDecimal(100)));

    }

    @Test
    void findByIdNotExistsTest() throws Exception {

        when(orderItemService.findById(1L)).thenReturn(Optional.empty());

        this.mockMvc.perform(
                        get("/api/v1/orderItem/id/{id}", 1L)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    void updateExistsTest() throws Exception {

        when(orderItemService.update(Mockito.any())).thenReturn(Optional.of(orderItemDTO));

        this.mockMvc.perform(
                        put("/api/v1/orderItem")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderItemPutCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.product.code").value("1234567890"))
                .andExpect(jsonPath("$.product.isAvailable").value(true))
                .andExpect(jsonPath("$.product.name").value("test"))
                .andExpect(jsonPath("$.product.description").value("test"))
                .andExpect(jsonPath("$.product.priceHrk").value(new BigDecimal(100)));

    }

    @Test
    void updateNotExistsTest() throws Exception {

        when(orderItemService.update(Mockito.any())).thenReturn(Optional.empty());

        this.mockMvc.perform(
                        put("/api/v1/orderItem")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderItemPutCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());

    }

    @Test
    void saveExistsTest() throws Exception {


        when(orderItemService.save(Mockito.any())).thenReturn(Optional.of(orderItemDTO));

        this.mockMvc.perform(
                        post("/api/v1/orderItem")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderItemPostCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.product.code").value("1234567890"))
                .andExpect(jsonPath("$.product.isAvailable").value(true))
                .andExpect(jsonPath("$.product.name").value("test"))
                .andExpect(jsonPath("$.product.description").value("test"))
                .andExpect(jsonPath("$.product.priceHrk").value(new BigDecimal(100)));

    }


    @Test
    void saveNotExistsTest() throws Exception {

        when(orderItemService.save(Mockito.any())).thenReturn(Optional.empty());

        this.mockMvc.perform(
                        post("/api/v1/orderItem")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderItemPostCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());

    }

    @Test
    void deleteByIdTest() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/orderItem/id/{id}", 1L)
        ).andExpect(status().isNoContent());

        verify(orderItemService).deleteById(1L);

    }
}