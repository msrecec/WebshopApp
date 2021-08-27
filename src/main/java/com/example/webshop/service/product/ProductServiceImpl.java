package com.example.webshop.service.product;

import com.example.webshop.dto.product.ProductDTO;
import com.example.webshop.mapping.mapper.product.ProductMapper;
import com.example.webshop.mapping.mapper.product.ProductMapperImpl;
import com.example.webshop.model.product.Product;
import com.example.webshop.model.product.ProductCommand;
import com.example.webshop.repository.product.ProductRepository;
import com.example.webshop.repository.product.ProductRepositoryCrud;
import com.example.webshop.repository.product.ProductRepositoryImpl;
import com.example.webshop.repository.product.ProductRepositoryJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductMapper mapper;
    private ProductRepositoryJpa productRepositoryJpa;
    private ProductRepositoryCrud productRepositoryCrud;
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductMapperImpl mapper, ProductRepositoryJpa productRepositoryJpa,
                              ProductRepositoryCrud productRepositoryCrud, ProductRepositoryImpl productRepository) {
        this.mapper = mapper;
        this.productRepositoryJpa = productRepositoryJpa;
        this.productRepositoryCrud = productRepositoryCrud;
        this.productRepository = productRepository;
    }

    @Override
    public Optional<ProductDTO> save(ProductCommand command) {
        return Optional.ofNullable(productRepositoryJpa.save(mapper.mapCommandToProduct(command))).map(mapper::mapProductToDTO);
    }

    @Override
    public List<ProductDTO> findAll() {
        return productRepositoryJpa.findAll().stream().map(mapper::mapProductToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> findByCode(String code) {
        return productRepositoryJpa.findByCode(code).map(mapper::mapProductToDTO);
    }

    @Override
    public Optional<ProductDTO> update(ProductCommand command) {
        return productRepository.update(mapper.mapCommandToProduct(command)).map(mapper::mapProductToDTO);
    }

    @Override
    public void deleteByCode(String code) {
        productRepository.delete(code);
    }
}