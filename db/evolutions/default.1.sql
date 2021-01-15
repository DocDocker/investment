# Currencies items

# --- !Ups

INSERT INTO Currency (id, name, symbol) VALUES(1, 'RUB', '₽');
INSERT INTO Currency (id, name, symbol) VALUES(2, 'USD', '$');
INSERT INTO Currency (id, name, symbol) VALUES(3, 'EUR', '€');


# --- !Downs

TRUNCATE TABLE Currency;