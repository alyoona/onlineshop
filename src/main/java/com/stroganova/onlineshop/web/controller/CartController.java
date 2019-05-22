package com.stroganova.onlineshop.web.controller;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
public class CartController {

    private CartService cartService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public ResponseEntity<?> viewCart() {
        LOGGER.info("Show the cart, sessionID: {} ", RequestContextHolder.currentRequestAttributes().getSessionId());
            return new ResponseEntity<>(cartService.viewCart(), HttpStatus.OK);
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestBody Product product) {
        LOGGER.info("Product added to cart, {}, sessionID: {}", product.getId(), RequestContextHolder.currentRequestAttributes().getSessionId());
        cartService.addToCart(product);
        return new ResponseEntity<>("Product added to cart, productId: " + product.getId(), HttpStatus.OK);
    }
}
