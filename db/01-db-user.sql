DROP USER IF EXISTS 'ppy_dbuser'@'%';

CREATE USER 'ppy_dbuser'@'%' IDENTIFIED BY 'pass1234';

FLUSH PRIVILEGES;
