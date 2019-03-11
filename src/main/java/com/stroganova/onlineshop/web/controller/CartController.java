package com.stroganova.onlineshop.web.controller;

import com.stroganova.onlineshop.entity.Cart;
import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.service.impl.ProductServiceDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(path = "/cart")
public class CartController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private ProductService productService;

    public CartController(ProductServiceDefault productService) {
        this.productService = productService;
    }

    @GetMapping
    public String cartPage(Model model, HttpSession session) {
        model.addAttribute("cart", getCart(session).getProducts());
        LOGGER.info("Show the cart");
        return "cart";
    }

    @PostMapping
    public String addToCart(HttpSession session, @RequestParam long id) {
        Cart cart = getCart(session);
        Product product = productService.getProduct(id);
        LOGGER.info("Product added to cart, {}: ", product);
        cart.addToCart(product);
        return "redirect:/products";
    }

    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            return cart;
        } else {
            session.setAttribute("cart", new Cart());
            return (Cart) session.getAttribute("cart");
        }
    }
}
