package ru.stepup.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.stepup.dto.ErrorResponse;
import org.springframework.web.client.RestClientException;
import ru.stepup.dto.PaymentRequestDto;
import ru.stepup.dto.ProductDto;
import ru.stepup.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    private final ProductService productService;

    public PaymentController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{userId}")
    public ResponseEntity<?> getProducts(@PathVariable Long userId) {
        List<ProductDto> products = productService.getProducts(userId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/execute")
    public ResponseEntity<?> execute(@RequestBody PaymentRequestDto paymentRequest) {
        return productService.execute(paymentRequest);
    }

}