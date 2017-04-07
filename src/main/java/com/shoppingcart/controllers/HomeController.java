package com.shoppingcart.controllers;

import com.shoppingcart.models.CartItem;
import com.shoppingcart.models.Product;
import com.shoppingcart.repository.CartItemRepository;
import com.shoppingcart.repository.ProductRepository;
import com.shoppingcart.repository.ShoppingCartRepository;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        BigDecimal total = new BigDecimal(0);

        for (CartItem item : cartItemList) {
            total = total.add(item.getSubTotal());
        }

        model.addAttribute("productList", productList);
        model.addAttribute("cartItemList", cartItemList);

        model.addAttribute("total", total.abs());

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

    @RequestMapping("/remove")
    public String remove(@RequestParam Long id) {

        cartItemRepository.delete(id);

        return "redirect:/";
    }

    @RequestMapping(value = "/updateCart", method = RequestMethod.POST)
    public String updateCart(HttpServletRequest request) {
        Long id = Long.parseLong(request.getParameter("id"));
        int qty = Integer.parseInt(request.getParameter("qty"));

        CartItem cartItem =cartItemRepository.findOne(id);
        cartItem.setQty(qty);
        cartItem.setSubTotal(new BigDecimal(cartItem.getProduct().getPrice()*qty).setScale(2, BigDecimal.ROUND_HALF_UP));

        cartItemRepository.save(cartItem);

        return "redirect:/";
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public String pay(HttpServletRequest request, Model model, @RequestParam double total) throws CardException, APIException, AuthenticationException, InvalidRequestException, APIConnectionException {
        // Set your secret key: remember to change this to your live secret key in production
        // See your keys here: https://dashboard.stripe.com/account/apikeys
        Stripe.apiKey = "sk_test_p5VUQTAeJjAbqQb6qZJBQDqu";

        // Token is created using Stripe.js or Checkout!
        // Get the payment token submitted by the form:
        String token = request.getParameter("stripeToken");

        // Charge the user's card:
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", (int) total*100);
        params.put("currency", "usd");
        params.put("description", "Example charge");
        params.put("source", token);

        model.addAttribute("paymentSuccess", true);

        Charge charge = Charge.create(params);

        List<CartItem> cartItemList = (List<CartItem>) cartItemRepository.findAll();

        for (CartItem item : cartItemList) {
            cartItemRepository.delete(item);
        }

        return "forward:/";
    }


}
