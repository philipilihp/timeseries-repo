CREATE  KEYSPACE ts_keyspace WITH REPLICATION = {
  'class' : 'SimpleStrategy',
  'replication_factor' : 1
};

CREATE TABLE timeseries (
  name text,
  version text,
  time timestamp,
  value decimal,
PRIMARY KEY ((name, version), time)
);