package com.example.webshop.service.product;

import com.example.webshop.command.product.ProductCommand;
import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.mapping.mapper.product.ProductMapper;
import com.example.webshop.mapping.mapper.product.ProductMapperImpl;
import com.example.webshop.model.product.Product;
import com.example.webshop.repository.product.ProductRepository;
import com.example.webshop.repository.product.ProductRepositoryImpl;
import com.example.webshop.repository.product.ProductRepositoryJpa;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ProductServiceImplTest {
    @MockBean(name = "productRepositoryJpa")
    private ProductRepositoryJpa productRepositoryJpa;
    @MockBean(name = "productRepository")
    private ProductRepository productRepository;
    @Autowired
    ProductService underTest;

    @Test
    void save() {
        // given

        Product product = Product.builder()
                .code("1234567891").description("test").isAvailable(true).name("test").priceHrk(new BigDecimal(100)).build();

        ProductCommand command = ProductCommand.builder()
                .code("1234567891").description("test").isAvailable(true).name("test").priceHrk(new BigDecimal(100)).build();

        when(productRepositoryJpa.save(any())).thenReturn(product);

        // when

        Optional<ProductDTO> productDTOOptional = underTest.save(command);

        // then

        assertThat(productDTOOptional.isPresent()).isTrue();
        assertThat(productDTOOptional.get().getCode()).isEqualToIgnoringCase("1234567891");
    }

    @Test
    void update () {
        // given

        Optional<Product> productOptional = Optional.of(Product.builder()
                .code("1234567890")
                .name("test")
                .priceHrk(new BigDecimal(1000))
                .description("test")
                .isAvailable(true).build());

        ProductCommand command = new ProductCommand("1234567890", "test", new BigDecimal(1000), "test", true);

        when(productRepository.update(any())).thenReturn(productOptional);

        // when

        Optional<ProductDTO> productDTOOptional = underTest.update(command);

        // then

        assertThat(productDTOOptional.isPresent()).isTrue();
        assertThat(productDTOOptional.get().getCode()).isEqualToIgnoringCase("1234567890");
        assertThat(productDTOOptional.get().getName()).isEqualToIgnoringCase("test");
    }

    @Test
    void findAll() {

        // given

        List<Product> productList = new ArrayList<>();

        productList.add(Product.builder().id(1L).code("1234567890")
                .name("Great product")
                .priceHrk(new BigDecimal(1000))
                .description("This is a great product")
                .isAvailable(true).build());

        productList.add(Product.builder().id(1L).code("1234567899")
                .name("Another Great product")
                .priceHrk(new BigDecimal(3000))
                .description("This is an another great product")
                .isAvailable(true).build());

        when(productRepositoryJpa.findAll()).thenReturn( productList);

        // when

        List<ProductDTO> productDTOS = underTest.findAll();

        // then

        assertThat(productDTOS.size()).isEqualTo(2);
        assertThat(productDTOS.get(0).getCode()).isEqualToIgnoringCase("1234567890");
        assertThat(productDTOS.get(1).getCode()).isEqualToIgnoringCase("1234567899");

    }

    @Test
    void findByCode() {

        // given

        Optional<Product> productOptional = Optional.of(Product.builder().id(1L).code("1234567890")
                .name("Great product")
                .priceHrk(new BigDecimal(1000))
                .description("This is a great product")
                .isAvailable(true).build());

        when(productRepositoryJpa.findByCode("1234567890")).thenReturn(productOptional);

        // when

        Optional<ProductDTO> productDTOOptional = underTest.findByCode("1234567890");

        // then

        assertThat(productDTOOptional.isPresent()).isTrue();
        assertThat(productDTOOptional.get().getCode()).isEqualToIgnoringCase("1234567890");
        assertThat(productDTOOptional.get().getName()).isEqualToIgnoringCase("Great product");
    }


}