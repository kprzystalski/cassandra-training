# Tworzenie nowej z NetworkTopologyStrategy
CREATE KEYSPACE IF NOT EXISTS codete WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 3 };
SELECT * FROM system_schema.keyspaces;


# Zmiana istniejacego keyspace, ktory ma SimpleStrategy
CREATE KEYSPACE codete WITH REPLICATION = { 'class' : 'SimpleStrategy','replication_factor' : 3 };

SELECT data_center FROM system.local;
ALTER KEYSPACE codete WITH replication = {'class': 'NetworkTopologyStrategy', 'datacenter1': '3'};

SELECT * FROM system_schema.keyspaces;