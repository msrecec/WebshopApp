package com.example.webshop.rest;

import com.example.webshop.command.customer.CustomerCommand;
import com.example.webshop.dto.customer.CustomerDTO;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class CustomerControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    CustomerDTO customerDTO;
    CustomerCommand customerCommand;

    @BeforeEach
    void setUp() {

        customerCommand = CustomerCommand.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .email("test@test.com").build();
    }

    @Test
    void findAllIT() throws Exception {

        this.mockMvc.perform(
                        get("/api/v1/customer")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));

    }

    @Test
    void findByIdExistsTest() throws Exception {

        this.mockMvc.perform(
                        get("/api/v1/customer/id/{id}", 1L)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Mislav"))
                .andExpect(jsonPath("$.lastName").value("Srecec"))
                .andExpect(jsonPath("$.email").value("mislav.srecec@outlook.com"));

    }

    @Test
    void findByIdNotExistsIT() throws Exception {
        this.mockMvc.perform(
                        get("/api/v1/customer/id/{id}", 2L)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    void updateExistsIT() throws Exception {

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
    void updateNotExistsIT() throws Exception {

        customerCommand.setId(2L);

        this.mockMvc.perform(
                        put("/api/v1/customer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customerCommand))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isConflict());

    }
}