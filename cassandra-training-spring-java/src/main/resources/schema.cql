DROP TABLE IF EXISTS "Order";
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS shop;
DROP TABLE IF EXISTS customer;

CREATE TABLE product (barcode text, id uuid, name text, price decimal, PRIMARY KEY (barcode, id)) WITH CLUSTERING ORDER BY (id ASC);
CREATE TABLE customer (fullname text, id uuid, PRIMARY KEY (id));
CREATE TABLE shop (address text, availableproductsquantity map<uuid, int>, email text, id uuid, locationx double, locationy double, name text, PRIMARY KEY (id));
CREATE TABLE "Order" (customerid uuid, id uuid, productid uuid, quantity int, shopid uuid, timestamp timestamp, PRIMARY KEY (id));