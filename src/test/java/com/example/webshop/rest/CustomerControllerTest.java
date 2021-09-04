package com.example.webshop.rest;

import com.example.webshop.command.customer.CustomerCommand;
import com.example.webshop.dto.customer.CustomerDTO;
import com.example.webshop.service.customer.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
class CustomerControllerTest {

    @MockBean(name = "customerService")
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    CustomerDTO customerDTO;
    CustomerCommand customerCommand;

    @BeforeEach
    void setUp() {
        customerDTO = CustomerDTO.builder()
                .firstName("test")
                .lastName("test")
                .email("test@test.com")
                .id(1L)
                .build();

        customerCommand = CustomerCommand.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();
    }

    @Test
    void findAllTest() throws Exception {

        // given

        List<CustomerDTO> customerDTOList = Arrays.asList(customerDTO);

        when(customerService.findCustomers()).thenReturn(customerDTOList);

        // when

        // then


        this.mockMvc.perform(
                        get("/api/v1/customer")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    void findByIdExistsTest() throws Exception {

        // given

        when(customerService.findCustomerById(1L)).thenReturn(Optional.of(customerDTO));

        // when

        // then


        this.mockMvc.perform(
                        get("/api/v1/customer/id/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("test"))
                .andExpect(jsonPath("$.lastName").value("test"))
                .andExpect(jsonPath("$.email").value("test@test.com"));

    }

    @Test
    void findByIdNotExistsTest() throws Exception {

        // given

        when(customerService.findCustomerById(1L)).thenReturn(Optional.empty());

        // when

        // then

        this.mockMvc.perform(
                        get("/api/v1/customer/id/{id}", 1L)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    void updateExistsTest() throws Exception {

        // given

        when(customerService.update(Mockito.any())).thenReturn(Optional.of(customerDTO));

        // when

        // then

        this.mockMvc.perform(
                        put("/api/v1/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customerCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("test"))
                .andExpect(jsonPath("$.lastName").value("test"))
                .andExpect(jsonPath("$.email").value("test@test.com"));

    }

    @Test
    void updateNotExistsTest() throws Exception {

        // given

        when(customerService.update(Mockito.any())).thenReturn(Optional.empty());

        // when

        // then

        this.mockMvc.perform(
                        put("/api/v1/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customerCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());

    }
}