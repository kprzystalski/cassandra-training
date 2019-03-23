DROP TABLE product;
CREATE TABLE product (title text, publisher text, id uuid, tags set<text>, PRIMARY KEY ((title, publisher), id)) WITH CLUSTERING ORDER BY (id DESC);