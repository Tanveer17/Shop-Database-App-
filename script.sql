CREATE DATABASE IF NOT EXISTS  shop_software;

USE shop_software;

DROP TABLE IF EXISTS customers;
CREATE TABLE customers (id INT PRIMARY KEY AUTO_INCREMENT,
                         first_name VARCHAR(50) NOT NULL,
                         last_name VARCHAR(50) NOT NULL,
                         phone_number VARCHAR(50),
                         email VARCHAR(50)
                        );

DROP TABLE IF EXISTS product_types;
CREATE TABLE product_types(id INT PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL
                           );

DROP TABLE IF EXISTS products;
CREATE TABLE products(id INT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(100) NOT NULL,
                      price DECIMAL(15,3) NOT NULL,
                      inStock INT,
                      product_type_id INT,
                      FOREIGN KEY (product_type_id) REFERENCES product_types(id)
                     );

DROP TABLE IF EXISTS sales;
CREATE TABLE sales(id INT PRIMARY KEY AUTO_INCREMENT,
                   sale_date DATE NOT NULL,
                    quantity INT NOT NULL,
                    customer_id INT,
                    product_id INT,
                    FOREIGN KEY (customer_id) REFERENCES customers(id),
                    FOREIGN KEY (product_id) REFERENCES products(id)
                    );

DROP TABLE IF EXISTS orders;
CREATE TABLE orders (id INT PRIMARY KEY AUTO_INCREMENT,
                      quantity INT NOT NULL,
                       order_date DATE NOT NULL,
                       customer_id INT,
                       product_id INT,
                       FOREIGN KEY (customer_id) REFERENCES customers(id),
                        FOREIGN KEY (product_id) REFERENCES products(id)
                      );




