package com.stroganova.onlineshop.web.controller;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.service.impl.ProductServiceDefault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private ProductService productService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public ProductController(ProductServiceDefault productService) {
        this.productService = productService;
    }

    @RequestMapping(value = {"/products", "/"}, method = RequestMethod.GET)
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

}
