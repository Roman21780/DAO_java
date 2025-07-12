package com.example.dao.controller;

import com.example.dao.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final CustomerRepository customerRepository;

    @GetMapping("/")
    public String home() {
        return "Добро пожаловать в Product API! Используйте /products/fetch-product?name=...";
    }

    @GetMapping("/fetch-product")
    public ResponseEntity<List<String>> fetchProduct(@RequestParam String name) {
        List<String> products = customerRepository.getProductName(name);
        return ResponseEntity.ok(products);
    }
}