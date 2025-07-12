package com.example.dao.repository;

import com.example.dao.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Теперь будут доступны все стандартные методы:
    // save(), saveAll(), findAll(), count() и др.
}