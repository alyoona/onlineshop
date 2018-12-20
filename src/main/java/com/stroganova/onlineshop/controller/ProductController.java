package com.stroganova.onlineshop.controller;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.entity.Session;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.web.templater.PageGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping(path = "/products", method = RequestMethod.GET)
    @ResponseBody
    public String getAll(@RequestAttribute("session") Session session) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("products", productService.getAll());
        pageVariables.put("session", session);

        PageGenerator pageGenerator = PageGenerator.instance();
        return pageGenerator.getPage("products.html", pageVariables);
    }


    @RequestMapping(path = "/products/add", method = RequestMethod.GET)
    @ResponseBody
    public String openAddProductForm(@RequestAttribute("session") Session session) {
        LOGGER.info("Start of processing the GET request by AddProductServlet");
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("session", session);
        LOGGER.info("End of processing the GET request by AddProductServlet");
        return PageGenerator.instance().getPage("add.html", pageVariables);
    }


    @RequestMapping(path = "/products/add", method = RequestMethod.POST)
    @ResponseBody
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("price") double price,
                             @RequestParam("picturePath") String picturePath,
                             @RequestParam("description") String description,
                             @RequestAttribute("session") Session session) {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setDescription(description);
        newProduct.setPicturePath(picturePath);
        productService.add(newProduct);
        return openAddProductForm(session);
    }


    @RequestMapping(path = "/cart", method = RequestMethod.GET)
    @ResponseBody
    public String openCart(@RequestAttribute("session") Session session) throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        if (session != null) {
            pageVariables.put("cart", session.getCart());
        }
        PageGenerator pageGenerator = PageGenerator.instance();
        return pageGenerator.getPage("cart.html", pageVariables);
    }


    @RequestMapping(path = "/cart", method = RequestMethod.POST)
    @ResponseBody
    public String addToCart(@RequestAttribute("session") Session session, @RequestParam(name = "id") long id) {
        Product product = productService.getProduct(id);
        session.addToCart(product);
        return getAll(session);
    }

}
