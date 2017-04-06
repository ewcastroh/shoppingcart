package com.shoppingcart.repository;

import com.shoppingcart.models.CartItem;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by z00382545 on 4/6/17.
 */
public interface CartItemRepository extends CrudRepository<CartItem, Long> {
}
