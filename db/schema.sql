CREATE ROLE "OnlineShopOwner" WITH
	NOLOGIN
	NOSUPERUSER
	NOCREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD 'root';
COMMENT ON ROLE "OnlineShopOwner" IS 'user for OnlineShop database';

CREATE DATABASE "OnlineShopDB"
    WITH 
    OWNER = "OnlineShopOwner"
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;

CREATE SCHEMA "OnlineShopSchema"
    AUTHORIZATION "OnlineShopOwner";

CREATE TABLE "OnlineShopSchema"."Products"
(
    id bigserial NOT NULL,
    name text NOT NULL,
    description text,
    price double precision NOT NULL,
    "picturePath" text,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE "OnlineShopSchema"."Products"
    OWNER to "OnlineShopOwner";

CREATE TABLE "OnlineShopSchema"."Users"
(
  id bigserial NOT NULL,
  login text NOT NULL,
  password text NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE "OnlineShopSchema"."Users"
OWNER to "OnlineShopOwner";