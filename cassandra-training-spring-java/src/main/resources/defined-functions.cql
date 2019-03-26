SELECT avg(total_value(quantity,unitprice)) FROM "Order";
SELECT id, min(quantity) FROM "Order";
SELECT id, max(total_value(quantity,unitprice)) FROM "Order";
