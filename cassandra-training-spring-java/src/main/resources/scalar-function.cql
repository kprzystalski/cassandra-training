USE codete;

# Scalar function - total_value
CREATE OR REPLACE FUNCTION total_value (quantity int, price decimal) RETURNS NULL ON NULL INPUT RETURNS decimal LANGUAGE java AS 'return price.multiply(java.math.BigDecimal.valueOf(quantity));';

## Test
SELECT total_value(quantity, singleProductPrice) FROM "Order";