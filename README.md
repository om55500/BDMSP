for database

CREATE DATABASE blood_bnk;

USE blood_bnk;

CREATE TABLE donor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    blood_group VARCHAR(5),
    age INT,
    gender VARCHAR(10),
    contact_number VARCHAR(15),
    email VARCHAR(100),
    address VARCHAR(255),
    last_donation_date DATE
);
