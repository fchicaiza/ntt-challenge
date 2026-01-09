-- Database Initialization Script for NTT Banking Challenge

-- Customer Service Tables
CREATE TABLE IF NOT EXISTS persons (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    gender VARCHAR(50),
    age INT,
    identification VARCHAR(50) UNIQUE,
    address VARCHAR(255),
    phone VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS customers (
    id VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    status BOOLEAN,
    FOREIGN KEY (id) REFERENCES persons(id)
);

-- Account Service Tables
CREATE TABLE IF NOT EXISTS accounts (
    id VARCHAR(255) PRIMARY KEY,
    account_number VARCHAR(50) UNIQUE,
    account_type VARCHAR(50),
    initial_balance DECIMAL(19, 2),
    balance DECIMAL(19, 2),
    status BOOLEAN,
    customer_id VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS movements (
    id VARCHAR(255) PRIMARY KEY,
    date TIMESTAMP,
    type VARCHAR(50),
    amount DECIMAL(19, 2),
    balance DECIMAL(19, 2),
    description VARCHAR(255),
    account_id VARCHAR(255),
    FOREIGN KEY (account_id) REFERENCES accounts(id)
);

-- Initial Data
-- Customer: Jose Lema
INSERT INTO persons (id, name, gender, age, identification, address, phone) 
VALUES ('1', 'Jose Lema', 'Masculino', 30, '1234567890', 'Otavalo sn y principal', '098254785');
INSERT INTO customers (id, password, status) VALUES ('1', '1234', true);

-- Customer: Marianela Montalvo
INSERT INTO persons (id, name, gender, age, identification, address, phone) 
VALUES ('2', 'Marianela Montalvo', 'Femenino', 28, '0987654321', 'Amazonas y NNUU', '097548965');
INSERT INTO customers (id, password, status) VALUES ('2', '5678', true);

-- Customer: Juan Osorio
INSERT INTO persons (id, name, gender, age, identification, address, phone) 
VALUES ('3', 'Juan Osorio', 'Masculino', 35, '1122334455', '13 junio y Equinoccial', '098874587');
INSERT INTO customers (id, password, status) VALUES ('3', '1245', true);

-- Accounts
INSERT INTO accounts (id, account_number, account_type, initial_balance, balance, status, customer_id)
VALUES ('a1', '478758', 'Ahorro', 2000.00, 1425.00, true, '1'); -- Retiro de 575 (2000 - 575 = 1425)
INSERT INTO accounts (id, account_number, account_type, initial_balance, balance, status, customer_id)
VALUES ('a2', '225487', 'Corriente', 100.00, 700.00, true, '2'); -- Depósito de 600 (100 + 600 = 700)
INSERT INTO accounts (id, account_number, account_type, initial_balance, balance, status, customer_id)
VALUES ('a3', '495878', 'Ahorros', 0.00, 150.00, true, '3'); -- Depósito de 150 (0 + 150 = 150)
INSERT INTO accounts (id, account_number, account_type, initial_balance, balance, status, customer_id)
VALUES ('a4', '585545', 'Corriente', 1000.00, 1000.00, true, '1'); -- Nueva cuenta 585545
INSERT INTO accounts (id, account_number, account_type, initial_balance, balance, status, customer_id)
VALUES ('a5', '496825', 'Ahorros', 540.00, 0.00, true, '2'); -- Retiro de 540 (540 - 540 = 0)

-- Movements (Based on point 4 of the image)
INSERT INTO movements (id, date, type, amount, balance, description, account_id)
VALUES ('m1', '2022-02-10 10:00:00', 'DEBIT', 575.00, 1425.00, 'Retiro de 575', 'a1');
INSERT INTO movements (id, date, type, amount, balance, description, account_id)
VALUES ('m2', '2022-02-10 11:00:00', 'CREDIT', 600.00, 700.00, 'Depósito de 600', 'a2');
INSERT INTO movements (id, date, type, amount, balance, description, account_id)
VALUES ('m3', '2022-02-08 09:00:00', 'CREDIT', 150.00, 150.00, 'Depósito de 150', 'a3');
INSERT INTO movements (id, date, type, amount, balance, description, account_id)
VALUES ('m4', '2022-02-08 10:00:00', 'DEBIT', 540.00, 0.00, 'Retiro de 540', 'a5');
