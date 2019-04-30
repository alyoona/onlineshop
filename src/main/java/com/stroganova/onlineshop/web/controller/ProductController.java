package com.stroganova.onlineshop.web.controller;

import com.stroganova.onlineshop.entity.Product;
import com.stroganova.onlineshop.service.ProductService;
import com.stroganova.onlineshop.service.impl.ProductServiceDefault;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class ProductController {

    private ProductService productService;

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public ProductController(ProductServiceDefault productService) {
        this.productService = productService;
    }

    @RequestMapping(value = {"/products", "/"}, method = RequestMethod.GET)
    public List<Product> getAllProducts() {
        LOGGER.info("Show all products");
        return productService.getAll();
    }

    @RequestMapping(path = "/products/add", method = RequestMethod.POST)
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product , BindingResult result) {

        if(result.hasErrors()){
            return  handleErrors(result);
        }

        LOGGER.info("Added Product {}: ", product);
        productService.add(product);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> delete(@PathVariable long productId){
        productService.delete(productId);

        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable long productId){
        Product product = productService.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }


    @PutMapping("/products/update")
    public ResponseEntity<?> update(@Valid @RequestBody Product product , BindingResult result){

        if(result.hasErrors()){
            return  handleErrors(result);
        }

        LOGGER.info("Updated Product {}: ", product);

        productService.update(product);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    private ResponseEntity<Map<String, String>> handleErrors(BindingResult result) {
            Map<String, String> errorMap = new HashMap<>();

            for(FieldError error: result.getFieldErrors()){
                errorMap.put(error.getField(), error.getDefaultMessage());
            }

            return  new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
    }

}
