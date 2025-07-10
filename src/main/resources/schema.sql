CREATE TABLE IF NOT EXISTS CUSTOMERS(
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    age INT,
    phone_number VARCHAR(15)
);

ALTER TABLE CUSTOMERS
ADD CONSTRAINT unique_phone UNIQUE (phone_number);

-- Генерация 100 случайных клиентов
DO $$
    BEGIN
        FOR i IN 1..100 LOOP
            INSERT INTO CUSTOMERS (name, surname, age, phone_number)
            SELECT
                (ARRAY['Ivan', 'Anna', 'Alex', 'Maria', 'Dmitry', 'Elena', 'Sergey', 'Olga'])[floor(random() * 8 + 1)] AS name,
                (ARRAY['Ivanov', 'Petrov', 'Sidorov', 'Smirnov', 'Kuznetsov', 'Popov', 'Volkov', 'Fedorov'])[floor(random() * 8 + 1)] AS surname,
                floor(random() * 50 + 18)::int AS age,  -- Возраст от 18 до 68
                '+7' || lpad(floor(random() * 900000000 + 100000000)::text, 9, '0') AS phone_number
            ON CONFLICT (phone_number) DO NOTHING;  -- Уникальность по номеру телефона
        END LOOP;
    END $$;

CREATE TABLE IF NOT EXISTS ORDERS(
    id SERIAL PRIMARY KEY,
    date DATE NOT NULL,
    customer_id INT NOT NULL,
    product_name VARCHAR(100) NOT NULL,
    amount INT NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES CUSTOMERS(id)
);

-- Генерация 200 случайных заказов (по 2 на клиента в среднем)
DO $$
    DECLARE
        cust_ids INT[];
    BEGIN
        -- Получаем все ID клиентов
        SELECT array_agg(id) INTO cust_ids FROM CUSTOMERS;

        -- Генерируем заказы
        FOR i IN 1..200 LOOP
            INSERT INTO ORDERS (date, customer_id, product_name, amount)
            SELECT
                CURRENT_DATE - (floor(random() * 365)::int * '1 day'::interval) AS date,  -- Дата за последний год
                cust_ids[floor(random() * array_length(cust_ids, 1)) + 1] AS customer_id,  -- Случайный клиент
                (ARRAY['Laptop', 'Smartphone', 'Tablet', 'Monitor', 'Keyboard', 'Printer', 'Headphones'])[floor(random() * 7 + 1)] AS product_name,
                floor(random() * 5 + 1)::int AS amount  -- Количество от 1 до 5
            ON CONFLICT DO NOTHING;
        END LOOP;
    END $$;


-- Проверка клиентов
SELECT * FROM customers LIMIT 10;

-- Проверка заказов
SELECT * FROM orders LIMIT 10;

-- Количество записей
SELECT count(*) FROM customers;
SELECT count(*) FROM orders;