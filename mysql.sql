USE invoice_db;

CREATE TABLE invoice (
    id INT AUTO_INCREMENT PRIMARY KEY,
    invoice_number VARCHAR(255),
    invoice_date DATE,
    client_name VARCHAR(255),
    client_address VARCHAR(255),
    item_desc VARCHAR(255),
    item_qty INT,
    item_price DOUBLE,
    item_tax DOUBLE,
    subtotal DOUBLE,
    total DOUBLE
);


desc invoice;

-- To view the data:
SELECT * FROM invoice;
