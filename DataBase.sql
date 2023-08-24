CREATE TABLE Employee (
                          id INT PRIMARY KEY,
                          name VARCHAR(255),
                          phoneNumber VARCHAR(20),
                          salary DOUBLE,
                          city VARCHAR(100),
                          joinDate DATE
);

CREATE TABLE Product (
                         id INT PRIMARY KEY,
                         number INT,
                         name VARCHAR(255),
                         price DOUBLE,
                         discount INT,
                         type VARCHAR(100)
);

CREATE TABLE Admin (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);
