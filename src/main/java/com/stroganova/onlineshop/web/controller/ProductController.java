package com.stroganova.onlineshop.web.controller;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String allProductsPage(Model model) {
        model.addAttribute("products", productService.getAll());
        LOGGER.info("Show all products");
        return "products";
    }


    @RequestMapping(path = "/products/add", method = RequestMethod.GET)
    public String addProductPage() {
        LOGGER.info("Open Add Product Form");
        return "add";
    }


    @RequestMapping(path = "/products/add", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute Product product) {
        LOGGER.info("Added Product");
        productService.add(product);
        return "redirect:/products/add";
    }


    @RequestMapping(path = "/cart", method = RequestMethod.GET)
    public String cartPage(Model model, @RequestAttribute Session session) {
        model.addAttribute("cart", session.getCart());
        LOGGER.info("Show the cart");
        return "cart";
    }


    @RequestMapping(path = "/cart", method = RequestMethod.POST)
    public String addToCart(@RequestAttribute Session session, @RequestParam long id) {

        Product product = productService.getProduct(id);
        LOGGER.info("Product added to cart, {}: ", product);
        session.addToCart(product);

        return "redirect:/products";
    }
}
