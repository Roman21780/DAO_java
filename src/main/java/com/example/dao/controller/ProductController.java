package com.example.dao.controller;

import com.example.dao.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CustomerRepository customerRepository;

    @Autowired
    public ProductController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/")
    public String home() {
        return "Добро пожаловать в Product API! Используйте /products/fetch-product?name=...";
    }

    @GetMapping("/fetch-product")
    public List<String> fetchProduct(@RequestParam String name) {
        return customerRepository.getProductName(name);
    }
}