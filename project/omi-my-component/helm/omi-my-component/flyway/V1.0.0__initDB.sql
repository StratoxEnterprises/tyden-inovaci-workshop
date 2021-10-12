CREATE TABLE reservation_warehouse
(
    id            VARCHAR PRIMARY KEY,
    first_name    VARCHAR,
    last_name     VARCHAR,
    email         VARCHAR,
    seat_id       VARCHAR,
    train_id      VARCHAR,
    train_name    VARCHAR,
    train_seats   INTEGER,
    date          DATE
);

CREATE SEQUENCE reservation_warehouse_id_sequence START 1;
