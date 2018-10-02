INSERT INTO OnlineShopSchema.Products(name, description, price, picturePath)
VALUES ('little toy', 'bear', 520.55, 'https://images.pexels.com/photos/207906/pexels-photo-207906.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260');

INSERT INTO OnlineShopSchema.Products(name, description, price, picturePath)
VALUES ('little toy', 'toy-donkey-plush-animal', 12.01, 'https://images.pexels.com/photos/63634/toy-donkey-plush-animal-63634.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260');

INSERT INTO OnlineShopSchema.Products(name, description, price, picturePath)
VALUES ('little toy', 'owl-glitter-stuffed-animal-cute', 98.60, 'https://images.pexels.com/photos/34533/owl-glitter-stuffed-animal-cute.jpg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260');

INSERT INTO OnlineShopSchema.Products(name, description, price, picturePath)
VALUES ('little toy', 'crocodile-sitting-alligator-animal', 99, 'https://images.pexels.com/photos/63637/crocodile-sitting-alligator-animal-63637.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260');

INSERT INTO OnlineShopSchema.Products(name, description, price, picturePath)
VALUES ('little toy', 'smilies-bank-sit-rest', 874.01, 'https://images.pexels.com/photos/160739/smilies-bank-sit-rest-160739.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260');

INSERT INTO OnlineShopSchema.Products(name, description, price, picturePath)
VALUES ('little toy', 'rose lion', 1580.64, 'https://images.pexels.com/photos/209600/pexels-photo-209600.jpeg?auto=compress&cs=tinysrgb&h=350');

INSERT INTO OnlineShopSchema.Users(login, password, salt) VALUES ('admin', '8209bb847335cdab1f926ef1e49a321d', '599b8dc9-452c-4b85-b8a6-6b086bd6f362');
INSERT INTO OnlineShopSchema.Users(login, password, salt) VALUES ('user', '30d368f96a2019b6fc66c4d82ceae315', 'b91bb09f-ae97-4e6b-b50e-c672215b9faa');

INSERT INTO onlineshopschema.roles(role) VALUES ('admin');
INSERT INTO onlineshopschema.roles(role) VALUES ('user');

INSERT INTO onlineshopschema.user_roles(user_id, role_id)
  select u.id, r.id  from OnlineShopSchema.Users u, OnlineShopSchema.roles r
  where u.login = 'admin' and r.role = 'admin';

INSERT INTO onlineshopschema.user_roles(user_id, role_id)
  select u.id, r.id  from OnlineShopSchema.Users u, OnlineShopSchema.roles r
  where u.login = 'user' and r.role = 'user';

