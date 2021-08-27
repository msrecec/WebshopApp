package com.example.webshop.rest;

import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.model.product.ProductCommand;
import com.example.webshop.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> save(@Valid @RequestBody final ProductCommand command) {
        return productService.save(command)
                .map(
                        productDTO -> ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(productDTO)
                )
                .orElseGet(
                        () -> ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .build()
                );
    }
}
