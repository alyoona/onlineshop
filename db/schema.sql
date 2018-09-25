CREATE DATABASE OnlineShopDB;

CREATE SCHEMA OnlineShopSchema;

CREATE TABLE OnlineShopSchema.Products
(
    id bigserial NOT NULL,
    name text NOT NULL,
    description text,
    price double precision NOT NULL,
    picturePath text,
    PRIMARY KEY (id)
);

CREATE TABLE OnlineShopSchema.Users
(
  id bigserial NOT NULL,
  login text NOT NULL,
  password text NOT NULL,
  PRIMARY KEY (id)
);
