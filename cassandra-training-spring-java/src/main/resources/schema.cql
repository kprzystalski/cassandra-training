# Schema
DROP TABLE IF EXISTS "Order";
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS shop;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS actionevent;
DROP TABLE IF EXISTS visitor;
DROP TABLE IF EXISTS visitoraction;

CREATE TYPE actionevent (additional map<text, text>, eventtarget text, eventtype text);
CREATE TABLE visitor (additional map<text, text>, age int, existingcustomerid uuid, gender varchar, id uuid, interests set<text>, ip text, location text, profession text, timestamp timestamp, type text, PRIMARY KEY (id));
CREATE TABLE shop (address text, availableproductsquantity map<uuid, int>, email text, id uuid, name text, PRIMARY KEY (id));
CREATE TABLE product (barcode text, id uuid, name text, PRIMARY KEY (barcode, id)) WITH CLUSTERING ORDER BY (id ASC);
CREATE TABLE "Order" (customerid uuid, id uuid, productid uuid, quantity int, shopid uuid, timestamp timestamp, unitprice decimal, PRIMARY KEY (id));
CREATE TABLE customer (email text, fullname text, id uuid, PRIMARY KEY (id));
CREATE TABLE visitoraction (event frozen<actionevent>, id uuid, timestamp timestamp, visitorid uuid, PRIMARY KEY (id));