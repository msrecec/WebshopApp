DELETE FROM product;
DELETE FROM order_item;
DELETE FROM webshop_order;
DELETE FROM customer;

INSERT INTO product(code, name, price_hrk, description, is_available)
VALUES ('1234567890', 'Great product', 1000, 'This is a great product', true);

INSERT INTO product(code, name, price_hrk, description, is_available)
VALUES ('1234567899', 'Another Great product', 3000, 'This is an another great product', true);

INSERT INTO customer(first_name, last_name, email) VALUES('Mislav', 'Srecec', 'mislav.srecec@outlook.com');

INSERT INTO webshop_order(customer_id, status, total_price_hrk, total_price_eur) VALUES(1, 'DRAFT', NULL, NULL);

INSERT INTO order_item(order_id, product_id, quantity) VALUES(1, 1, 1);
INSERT INTO order_item(order_id, product_id, quantity) VALUES(1, 2, 2);