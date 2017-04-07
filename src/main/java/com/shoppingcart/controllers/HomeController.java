package com.shoppingcart.controllers;

import com.shoppingcart.models.CartItem;
import com.shoppingcart.models.Product;
import com.shoppingcart.repository.CartItemRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by z00382545 on 4/6/17.
 */

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @RequestMapping("/")
    public String home(Model model) {
        List<Product> productList = (List<Product>) productRepository.findAll();

        List<CartItem> cartItemList = (List<CartItem>) cartItemRepository.findAll();

        model.addAttribute("productList", productList);
        model.addAttribute("cartItemList", cartItemList);

        return "home";
    }

    @RequestMapping("/addToCart")
    public String addToCart(@RequestParam Long id) {
        Product product = productRepository.findOne(id);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQty(1);
        cartItem.setSubTotal(new BigDecimal(product.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP));
        cartItemRepository.save(cartItem);

        return "redirect:/";
    }


}
