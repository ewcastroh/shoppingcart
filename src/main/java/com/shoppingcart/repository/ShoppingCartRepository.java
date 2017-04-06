package com.shoppingcart.repository;

import com.shoppingcart.models.CartItem;
import com.shoppingcart.models.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by z00382545 on 4/6/17.
 */
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {


}
