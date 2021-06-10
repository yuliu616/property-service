GRANT SELECT, INSERT, UPDATE, DELETE, TRIGGER
  ON hellodb.ppy_property_id TO 'ppy_dbuser'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE, TRIGGER
  ON hellodb.ppy_property TO 'ppy_dbuser'@'%';

FLUSH PRIVILEGES;
