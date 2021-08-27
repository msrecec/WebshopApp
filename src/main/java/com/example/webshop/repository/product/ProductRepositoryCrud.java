package com.example.webshop.repository.product;

import com.example.webshop.model.product.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepositoryCrud extends CrudRepository<Product, Long> {

}
