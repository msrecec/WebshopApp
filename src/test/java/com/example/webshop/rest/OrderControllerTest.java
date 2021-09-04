package com.example.webshop.rest;

import com.example.webshop.command.order.OrderPostCommand;
import com.example.webshop.command.order.OrderPutCommand;
import com.example.webshop.command.order.nested.CustomerNestedInOrderCommand;
import com.example.webshop.command.order.nested.OrderItemNestedInOrderCommand;
import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.dto.order.OrderDTO;
import com.example.webshop.dto.orderItem.OrderItemDTO;
import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.model.customer.Customer;
import com.example.webshop.model.order.Order;
import com.example.webshop.model.order.Status;
import com.example.webshop.model.orderItem.OrderItem;
import com.example.webshop.model.product.Product;
import com.example.webshop.service.order.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTest {

    @MockBean(name = "orderService")
    OrderService orderService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    OrderDTO orderDTO;
    CustomerDTO customerDTO;
    ProductDTO productDTO;
    OrderItemDTO orderItemDTO;

    OrderPostCommand orderPostCommand;
    OrderPutCommand orderPutCommand;

    @BeforeEach
    void setUp() {

        // order set up

        orderDTO = OrderDTO.builder()
                .id(1L)
                .status(Status.DRAFT)
                .totalPriceHrk(new BigDecimal(100))
                .totalPriceEur(new BigDecimal(100))
                .build();

        customerDTO = CustomerDTO.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();

        productDTO = ProductDTO.builder()
                .code("1234567890")
                .description("test")
                .isAvailable(true)
                .name("test")
                .priceHrk(new BigDecimal(100)).build();

        orderItemDTO = OrderItemDTO.builder().quantity(1).id(1L).product(productDTO).build();

        List<OrderItemDTO> orderItemDTOList = new ArrayList<>();
        orderItemDTOList.add(orderItemDTO);

        orderDTO.setCustomer(customerDTO);
        orderDTO.setOrderItems(orderItemDTOList);

        // orderItem set up

        OrderItemNestedInOrderCommand item = OrderItemNestedInOrderCommand.builder().quantity(1).code("1234567890").build();

        List<OrderItemNestedInOrderCommand> itemList = new ArrayList<>();

        itemList.add(item);

        CustomerNestedInOrderCommand customerNestedInOrderCommand = CustomerNestedInOrderCommand.builder()
                .email(customerDTO.getEmail()).firstName(customerDTO.getFirstName()).lastName(customerDTO.getLastName()).build();

        orderPostCommand = OrderPostCommand.builder().customer(customerNestedInOrderCommand).orderItems(itemList).build();

        orderPutCommand = OrderPutCommand.builder().id(1L).status(Status.DRAFT).build();

    }

    @Test
    void findAllTest() throws Exception {

        List<OrderDTO> orderDTOList = new ArrayList<>();

        orderDTOList.add(orderDTO);

        // given

        when(orderService.findAll()).thenReturn(orderDTOList);

        // when

        // then

        this.mockMvc.perform(
                        get("/api/v1/order")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void findByIdExistsTest() throws Exception {

        when(orderService.findById(1L)).thenReturn(Optional.of(orderDTO));

        this.mockMvc.perform(
                        get("/api/v1/order/id/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(1))
                .andExpect(jsonPath("$.orderItems[0].id").value(1))
                .andExpect(jsonPath("$.orderItems[0].product.name").value("test"))
                .andExpect(jsonPath("$.orderItems[0].product.description").value("test"))
                .andExpect(jsonPath("$.orderItems[0].product.code").value("1234567890"))
                .andExpect(jsonPath("$.customer.firstName").value("test"))
                .andExpect(jsonPath("$.customer.lastName").value("test"))
                .andExpect(jsonPath("$.customer.email").value("test@test.com"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value(Status.DRAFT.getStatus()))
                .andExpect(jsonPath("$.totalPriceHrk").value(100))
                .andExpect(jsonPath("$.totalPriceEur").value(100));
    }


    @Test
    void findByIdNotExistsTest() throws Exception {

        when(orderService.findById(1L)).thenReturn(Optional.empty());

        this.mockMvc.perform(
                        get("/api/v1/order/id/{id}", 1L)
                )
                .andExpect(status().isNotFound());


    }

    @Test
    void finalizeOrderExistsTest() throws Exception {

        when(orderService.finalizeOrder(1L)).thenReturn(Optional.of(orderDTO));

        this.mockMvc.perform(
                        post("/api/v1/order/finalize/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(1))
                .andExpect(jsonPath("$.orderItems[0].id").value(1))
                .andExpect(jsonPath("$.orderItems[0].product.name").value("test"))
                .andExpect(jsonPath("$.orderItems[0].product.description").value("test"))
                .andExpect(jsonPath("$.orderItems[0].product.code").value("1234567890"))
                .andExpect(jsonPath("$.customer.firstName").value("test"))
                .andExpect(jsonPath("$.customer.lastName").value("test"))
                .andExpect(jsonPath("$.customer.email").value("test@test.com"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value(Status.DRAFT.getStatus()))
                .andExpect(jsonPath("$.totalPriceHrk").value(100))
                .andExpect(jsonPath("$.totalPriceEur").value(100));

    }

    @Test
    void finalizeOrderNotExistsTest() throws Exception {

        when(orderService.finalizeOrder(1L)).thenReturn(Optional.empty());

        this.mockMvc.perform(
                        post("/api/v1/order/finalize/{id}", 1L)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    void saveExistsTest() throws Exception {
        when(orderService.save(Mockito.any())).thenReturn(Optional.of(orderDTO));

        this.mockMvc.perform(
                        post("/api/v1/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderPostCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.orderItems[0].quantity").value(1))
                .andExpect(jsonPath("$.orderItems[0].id").value(1))
                .andExpect(jsonPath("$.orderItems[0].product.name").value("test"))
                .andExpect(jsonPath("$.orderItems[0].product.description").value("test"))
                .andExpect(jsonPath("$.orderItems[0].product.code").value("1234567890"))
                .andExpect(jsonPath("$.customer.firstName").value("test"))
                .andExpect(jsonPath("$.customer.lastName").value("test"))
                .andExpect(jsonPath("$.customer.email").value("test@test.com"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value(Status.DRAFT.getStatus()))
                .andExpect(jsonPath("$.totalPriceHrk").value(100))
                .andExpect(jsonPath("$.totalPriceEur").value(100));

    }

    @Test
    void saveNotExistsTest() throws Exception {
        when(orderService.save(Mockito.any())).thenReturn(Optional.empty());

        this.mockMvc.perform(
                        post("/api/v1/order")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(orderPostCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());

    }

    @Test
    void updateExistsTest() throws Exception {
        when(orderService.update(Mockito.any())).thenReturn(Optional.of(orderDTO));

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
                .andExpect(jsonPath("$.orderItems[0].product.name").value("test"))
                .andExpect(jsonPath("$.orderItems[0].product.description").value("test"))
                .andExpect(jsonPath("$.orderItems[0].product.code").value("1234567890"))
                .andExpect(jsonPath("$.customer.firstName").value("test"))
                .andExpect(jsonPath("$.customer.lastName").value("test"))
                .andExpect(jsonPath("$.customer.email").value("test@test.com"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.status").value(Status.DRAFT.getStatus()))
                .andExpect(jsonPath("$.totalPriceHrk").value(100))
                .andExpect(jsonPath("$.totalPriceEur").value(100));
    }

    @Test
    void updateNotExistsTest() throws Exception {
        when(orderService.update(Mockito.any())).thenReturn(Optional.empty());

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

    }
}