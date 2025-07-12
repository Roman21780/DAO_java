package com.example.dao.repository.impl;

import com.example.dao.entity.Customer;
import com.example.dao.repository.CustomerRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long count() {
        return entityManager.createQuery("SELECT COUNT(c) FROM Customer c", Long.class)
                .getSingleResult();
    }

    @Override
    public void saveAll(List<Customer> customers) {
        for (Customer customer : customers) {
            entityManager.persist(customer);
        }
        entityManager.flush();
    }

    @Override
    public List<String> getProductName(String name) {
        return entityManager.createQuery(
                        "SELECT o.productName FROM Order o WHERE LOWER(o.customer.name) = LOWER(:name)",
                        String.class)
                .setParameter("name", name)
                .getResultList();
    }
}
