package ru.stepup.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepup.dto.Product;
import ru.stepup.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Product> getProductsByUserId(@PathVariable Long userId) {
        return productService.findByUserId(userId);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getProductsByUserIdAndAccount(@RequestParam Long userId, @RequestParam(required = false) String account) {
        if (account == null) {
            List<Product> products = productService.findByUserId(userId);
            return new ResponseEntity<>(products, HttpStatus.OK);
        } else {
            Product product = productService.findByUserIdAndAccount(userId, account);
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

}