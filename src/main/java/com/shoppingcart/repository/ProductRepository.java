package com.shoppingcart.repository;

import com.shoppingcart.models.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by z00382545 on 4/6/17.
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByName(String name);
}
