package com.example.dao.utils;

import com.example.dao.entity.Customer;
import com.example.dao.entity.Order;
import com.example.dao.repository.CustomerRepository;
import com.example.dao.repository.OrderRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;

    private static final String[] FIRST_NAMES = {"Ivan", "Anna", "Alex", "Maria", "Dmitry", "Elena", "Sergey", "Olga"};
    private static final String[] LAST_NAMES = {"Ivanov", "Petrov", "Sidorov", "Smirnov", "Kuznetsov", "Popov", "Volkov", "Fedorov"};
    private static final String[] PRODUCTS = {"Laptop", "Smartphone", "Tablet", "Monitor", "Keyboard", "Printer", "Headphones"};
    private static final Random random = new Random();

    @PostConstruct
    @Transactional
    public void init() {
        if (customerRepo.count() == 0) {
            // Генерация 100 клиентов
            List<Customer> customers = IntStream.range(0, 100)
                    .mapToObj(i -> generateRandomCustomer())
                    .collect(Collectors.toList());
            customerRepo.saveAll(customers);

            // Генерация 200 заказов
            List<Order> orders = IntStream.range(0, 200)
                    .mapToObj(i -> generateRandomOrder(customers))
                    .collect(Collectors.toList());
            orderRepo.saveAll(orders);
        }
    }

    private Customer generateRandomCustomer() {
        Customer customer = new Customer();
        customer.setName(FIRST_NAMES[random.nextInt(FIRST_NAMES.length)]);
        customer.setSurname(LAST_NAMES[random.nextInt(LAST_NAMES.length)]);
        customer.setAge(18 + random.nextInt(50)); // 18-67 лет
        customer.setPhoneNumber("+7" + (100_000_000 + random.nextInt(900_000_000)));
        return customer;
    }

    private Order generateRandomOrder(List<Customer> customers) {
        Order order = new Order();
        order.setCustomer(customers.get(random.nextInt(customers.size())));
        order.setProductName(PRODUCTS[random.nextInt(PRODUCTS.length)]);
        order.setAmount(1 + random.nextInt(5)); // 1-5 единиц
        order.setDate(generateRandomDate());
        return order;
    }

    private LocalDate generateRandomDate() {
        long minDay = LocalDate.now().minusYears(1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }
}
