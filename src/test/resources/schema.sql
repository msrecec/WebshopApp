DROP TABLE IF EXISTS product;

CREATE TABLE product (
    id INT AUTO_INCREMENT,
    code VARCHAR(100),
    name VARCHAR(100),
    price_hrk DECIMAL(10, 2),
    description VARCHAR(255),
    is_available BOOLEAN,
    PRIMARY KEY(ID),
    UNIQUE(code)
);

DROP TABLE IF EXISTS order_item

CREATE TABLE order_item (
    id INT AUTO_INCREMENT,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT,
    PRIMARY KEY(ID),
    constraint order_id foreign key (order_id) references webshop_order(id),
    constraint product_id foreign key (product_id) references product(id)
);

DROP TABLE IF EXISTS webshop_order

CREATE TABLE webshop_order (
    id INT AUTO_INCREMENT,
    customer_id INT NOT NULL,
    status VARCHAR(100) NOT NULL,
    total_price_hrk DECIMAL(10, 2),
    total_price_eur DECIMAL(10, 2),
    PRIMARY KEY(ID)
);

DROP TABLE IF EXISTS customer

CREATE TABLE customer (
    id INT AUTO_INCREMENT,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(100)
);
