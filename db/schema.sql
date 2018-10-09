
CREATE SCHEMA onlineshopschema;

CREATE TABLE onlineshopschema.products
(
    id bigserial NOT NULL,
    name text NOT NULL,
    description text,
    price double precision NOT NULL,
    picturePath text,
    PRIMARY KEY (id)
);

CREATE TABLE onlineshopschema.users
(
  id bigserial NOT NULL,
  login text NOT NULL UNIQUE,
  password text NOT NULL,
  salt text NOT NULL ,
  PRIMARY KEY (id)
);

CREATE TABLE onlineshopschema.roles
(
  id bigserial NOT NULL,
  role text NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE onlineshopschema.user_roles
(
  id bigserial NOT NULL,
  user_id bigserial NOT NULL,
  role_id bigserial NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE onlineshopschema.user_roles
  ADD CONSTRAINT user_roles_users_id_fk
FOREIGN KEY (user_id) REFERENCES onlineshopschema.users (id);

ALTER TABLE onlineshopschema.user_roles
  ADD CONSTRAINT user_roles_roles_id_fk
FOREIGN KEY (role_id) REFERENCES onlineshopschema.roles (id);