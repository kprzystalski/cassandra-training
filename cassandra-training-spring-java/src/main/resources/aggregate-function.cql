USE codete;

# Aggregate function example - average
## Store state in tuple<int, bigint>, start with state (0,0), for each row increment first counter (number of rows processed) and add current row value to the second value of tuple (current sum)
CREATE OR REPLACE FUNCTION avgStateRow ( state tuple<int,bigint>, val int ) CALLED ON NULL INPUT RETURNS tuple<int,bigint> LANGUAGE java AS
  'if (val !=null) { state.setInt(0, state.getInt(0)+1); state.setLong(1, state.getLong(1)+val.intValue()); } return state;';

## When all rows are processed, the final result (average value) is equal to dividing the second value of the tuple (sum) by the first one (counter)
CREATE OR REPLACE FUNCTION avgFinal ( state tuple<int,bigint> ) CALLED ON NULL INPUT RETURNS double LANGUAGE java AS
   'double r = 0; if (state.getInt(0) == 0) return null; r = state.getLong(1); r/= state.getInt(0); return Double.valueOf(r);';

## Create aggregate function composed of the two above
CREATE AGGREGATE IF NOT EXISTS codete_average ( int )
SFUNC avgStateRow STYPE tuple<int,bigint> FINALFUNC avgFinal INITCOND (0,0);

## Test
SELECT * FROM "Order";
SELECT codete_average(quantity) FROM "Order";