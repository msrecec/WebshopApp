package com.example.webshop.rest;

import com.example.webshop.command.product.ProductCommand;
import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.service.product.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
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
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
class ProductControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    ProductCommand command;

    @BeforeEach
    void setUp() {

        this.command = ProductCommand.builder()
                .code("1234567890")
                .isAvailable(true)
                .name("test")
                .description("test")
                .priceHrk(new BigDecimal(100))
                .build();

    }


    @Test
    void getAllProductsExistsTest() throws Exception {

        this.mockMvc.perform(
                        get("/api/v1/product")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));

    }

// VALUES ('1234567890', 'Great product', 1000, 'This is a great product', true);

    @Test
    void getProductByCodeExistsTest() throws Exception {

        this.mockMvc.perform(
                        get("/api/v1/product/code/{code}", "1234567890")
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("1234567890"))
                .andExpect(jsonPath("$.isAvailable").value(true))
                .andExpect(jsonPath("$.name").value("Great product"))
                .andExpect(jsonPath("$.description").value("This is a great product"));

    }

    @Test
    @DirtiesContext
    void saveExistsTest() throws Exception {
        command.setCode("1234567891");

        this.mockMvc.perform(
                        post("/api/v1/product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(command))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("1234567891"))
                .andExpect(jsonPath("$.isAvailable").value(true))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.description").value("test"))
                .andExpect(jsonPath("$.priceHrk").value(new BigDecimal(100)));

    }

    @Test
    @DirtiesContext
    void updateExistsTest() throws Exception {

        this.mockMvc.perform(
                        put("/api/v1/product")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(command))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("1234567890"))
                .andExpect(jsonPath("$.isAvailable").value(true))
                .andExpect(jsonPath("$.name").value("test"))
                .andExpect(jsonPath("$.description").value("test"))
                .andExpect(jsonPath("$.priceHrk").value(new BigDecimal(100)));
    }
}