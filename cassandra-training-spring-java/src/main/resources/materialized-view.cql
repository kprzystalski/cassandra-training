CREATE MATERIALIZED VIEW aged_customer
AS SELECT age, fullName
FROM customer
WHERE age IS NOT NULL
PRIMARY KEY (age, id);

SELECT fullName, age FROM aged_customer WHERE age = 17;