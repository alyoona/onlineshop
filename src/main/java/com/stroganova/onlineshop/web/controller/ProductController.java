package com.stroganova.onlineshop.web.controller;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.ProductService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String getAll(@ModelAttribute("model") ModelMap model, @RequestAttribute("session") Session session) {
        model.addAttribute("products", productService.getAll());
        model.addAttribute("session", session);
        LOGGER.info("Show all products");
        return "products";

    }


    @RequestMapping(path = "/products/add", method = RequestMethod.GET)
    public String openAddProductForm(@ModelAttribute("model") ModelMap model, @RequestAttribute("session") Session session) {
        LOGGER.info("Open Add Product Form");
        model.addAttribute("session", session);
        return "add";
    }


    @RequestMapping(path = "/products/add", method = RequestMethod.POST)
    public String addProduct(@ModelAttribute("product") Product product) {
        LOGGER.info("Added Product");
        productService.add(product);
        return "redirect:/products/add";
    }


    @RequestMapping(path = "/cart", method = RequestMethod.GET)
    public String openCart(@ModelAttribute("model") ModelMap model, @RequestAttribute("session") Session session) throws IOException {
        if (session != null) {
            model.addAttribute("cart", session.getCart());
        }
        LOGGER.info("Show the cart");
        return "cart";
    }


    @RequestMapping(path = "/cart", method = RequestMethod.POST)
    public String addToCart(@RequestAttribute("session") Session session, @RequestParam(name = "id") long id) {
        Product product = productService.getProduct(id);
        session.addToCart(product);
        return "redirect:/products";
    }
}
