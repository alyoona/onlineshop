package com.stroganova.onlineshop.web.controller;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

@RestController
@CrossOrigin//(origins = "http://localhost:3000", allowCredentials = "true", maxAge = 6000)
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

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE,"JSESSIONID="+RequestContextHolder.currentRequestAttributes().getSessionId());

        return new ResponseEntity<>("Product added to cart, productId: " + product.getId(),headers, HttpStatus.OK);
    }

/*    private Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            return cart;
        } else {
            session.setAttribute("cart", new Cart());
            return (Cart) session.getAttribute("cart");
        }
    }*/
}
