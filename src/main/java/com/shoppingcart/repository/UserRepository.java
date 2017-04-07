package com.shoppingcart.repository;

import com.shoppingcart.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by z00382545 on 4/7/17.
 */
public interface UserRepository extends CrudRepository<User, Long> {
}
