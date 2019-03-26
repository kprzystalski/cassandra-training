# Schema
DROP TABLE IF EXISTS "Order";
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS shop;
DROP TABLE IF EXISTS customer;

CREATE TABLE product (barcode text, id uuid, name text, PRIMARY KEY (barcode, id)) WITH CLUSTERING ORDER BY (id ASC);
CREATE TABLE customer (age int, fullname text, id uuid, PRIMARY KEY (id));
CREATE TABLE "Order" (customerid uuid, id uuid, productid uuid, quantity int, shopid uuid, unitprice decimal, timestamp timestamp, PRIMARY KEY (id));
CREATE TABLE shop (address text, availableproductsquantity map<uuid, int>, email text, id uuid, name text, PRIMARY KEY (id));