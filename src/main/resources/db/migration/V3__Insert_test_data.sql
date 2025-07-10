INSERT INTO CUSTOMERS (name, surname, age, phone_number) VALUES
    ('Alexey', 'Petrov', 30, '+79161234567'),
    ('Ivan', 'Ivanov', 25, '+79167654321');

INSERT INTO ORDERS (date, customer_id, product_name, amount) VALUES
    (CURRENT_DATE, 1, 'Laptop', 1),
    (CURRENT_DATE, 1, 'Mouse', 2),
    (CURRENT_DATE, 2, 'Keyboard', 1);