package com.example.webshop.rest;

import com.example.webshop.command.order.OrderPostCommand;
import com.example.webshop.command.order.OrderPutCommand;
import com.example.webshop.command.order.nested.CustomerNestedInOrderCommand;
import com.example.webshop.command.order.nested.OrderItemNestedInOrderCommand;
import com.example.webshop.model.order.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    OrderPostCommand orderPostCommand;
    OrderPutCommand orderPutCommand;

    @BeforeEach
    void setUp() {

        // orderItem set up

        OrderItemNestedInOrderCommand item = OrderItemNestedInOrderCommand.builder().quantity(1).code("1234567890").build();

        List<OrderItemNestedInOrderCommand> itemList = new ArrayList<>();

        itemList.add(item);

        CustomerNestedInOrderCommand customerNestedInOrderCommand = CustomerNestedInOrderCommand.builder()
                .email("test@test.com").firstName("test").lastName("test").build();

        orderPostCommand = OrderPostCommand.builder().customer(customerNestedInOrderCommand).orderItems(itemList).build();

        orderPutCommand = OrderPutCommand.builder().id(1L).status(Status.SUBMITTED).build();

    }

    @Test
    void findAllIT() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/order")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void findByIdExistsIT() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/order/id/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(1))
                .andExpect(jsonPath("$.orderItems[0].id").value(1))
                .andExpect(jsonPath("$.orderItems[0].product.name").value("Great product"))
                .andExpect(jsonPath("$.orderItems[0].product.description").value("This is a great product"))
                .andExpect(jsonPath("$.orderItems[0].product.code").value("1234567890"))
                .andExpect(jsonPath("$.customer.firstName").value("Mislav"))
                .andExpect(jsonPath("$.customer.lastName").value("Srecec"))
                .andExpect(jsonPath("$.customer.email").value("mislav.srecec@outlook.com"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value(Status.DRAFT.getStatus()));
    }


    @Test
    void findByIdNotExistsIT() throws Exception {

        this.mockMvc.perform(
                        get("/api/v1/order/id/{id}", 2L)
                )
                .andExpect(status().isNotFound());


    }

    @Test
    void finalizeOrderExistsIT() throws Exception {
        this.mockMvc.perform(
                        post("/api/v1/order/finalize/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(1))
                .andExpect(jsonPath("$.orderItems[0].id").value(1))
                .andExpect(jsonPath("$.orderItems[0].product.name").value("Great product"))
                .andExpect(jsonPath("$.orderItems[0].product.description").value("This is a great product"))
                .andExpect(jsonPath("$.orderItems[0].product.code").value("1234567890"))
                .andExpect(jsonPath("$.customer.firstName").value("Mislav"))
                .andExpect(jsonPath("$.customer.lastName").value("Srecec"))
                .andExpect(jsonPath("$.customer.email").value("mislav.srecec@outlook.com"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value(Status.SUBMITTED.getStatus()));

    }

    @Test
    void finalizeOrderNotExistsIT() throws Exception {
        this.mockMvc.perform(
                        post("/api/v1/order/finalize/{id}", 2L)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    void saveExistsIT() throws Exception {
        this.mockMvc.perform(
                        post("/api/v1/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderPostCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(1))
                .andExpect(jsonPath("$.orderItems[0].id").value(3))
                .andExpect(jsonPath("$.orderItems[0].product.name").value("Great product"))
                .andExpect(jsonPath("$.orderItems[0].product.description").value("This is a great product"))
                .andExpect(jsonPath("$.orderItems[0].product.code").value("1234567890"))
                .andExpect(jsonPath("$.customer.firstName").value("test"))
                .andExpect(jsonPath("$.customer.lastName").value("test"))
                .andExpect(jsonPath("$.customer.email").value("test@test.com"))
                .andExpect(jsonPath("$.customer.id").value(2))
                .andExpect(jsonPath("$.status").value(Status.DRAFT.getStatus()));

    }


    @Test
    void updateExistsTest() throws Exception {
        this.mockMvc.perform(
                        put("/api/v1/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderPutCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(1))
                .andExpect(jsonPath("$.orderItems[0].id").value(1))
                .andExpect(jsonPath("$.orderItems[0].product.name").value("Great product"))
                .andExpect(jsonPath("$.orderItems[0].product.description").value("This is a great product"))
                .andExpect(jsonPath("$.orderItems[0].product.code").value("1234567890"))
                .andExpect(jsonPath("$.customer.firstName").value("Mislav"))
                .andExpect(jsonPath("$.customer.lastName").value("Srecec"))
                .andExpect(jsonPath("$.customer.email").value("mislav.srecec@outlook.com"))
                .andExpect(jsonPath("$.customer.id").value(1))
                .andExpect(jsonPath("$.status").value(Status.SUBMITTED.getStatus()));
    }

    @Test
    void updateNotExistsTest() throws Exception {

        orderPutCommand.setId(2L);

        this.mockMvc.perform(
                        put("/api/v1/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderPutCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());
    }

    @Test
    void delete() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.delete("/api/v1/order/id/{id}", 1L)
        ).andExpect(status().isNoContent());

        this.mockMvc.perform(
                        get("/api/v1/order")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }
}