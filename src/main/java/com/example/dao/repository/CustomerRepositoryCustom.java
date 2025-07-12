package com.example.dao.repository;

import com.example.dao.entity.Customer;

import java.util.List;

public interface CustomerRepositoryCustom {
    long count();
    void saveAll(List<Customer> customers);
    List<String> getProductName(String name);
}
