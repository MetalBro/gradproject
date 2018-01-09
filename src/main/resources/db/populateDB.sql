-- DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurants;
DELETE FROM dishes;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password, ROLE) VALUES
  ('User', 'user@yandex.ru', 'password', 'ROLE_USER'),        --100000
  ('Admin', 'admin@gmail.com', 'admin', 'ROLE_ADMIN');        --100001

INSERT INTO users (name, email, password) VALUES
  ('User_1', 'user@mail.ru', 'password_1'),                   --100002
  ('User_2', 'user@bk.ru', 'password_2'),                     --100003
  ('User_3', 'user@list.ru', 'password_3');                   --100004

INSERT INTO restaurants (name, address, cookery) VALUES
  ('Nar-Sharab', 'Naberejnaya, 121', 'Caucasian'),            --100005
  ('Svoya kompanya', 'Lesoparkovaya, 15', 'Russian'),         --100006
  ('Pizzburg', 'Prospect Lenina, 29', 'Italian');             --100007

INSERT INTO dishes (name, price, restaurant_id, date) VALUES
  ('Харчо', 500, 100005, '2017-06-21'),
  ('Котлеты', 239.1, 100005, '2017-06-21'),
  ('Пюре', 109.2, 100005, '2017-06-21'),
  ('Щи', 500, 100006, '2016-05-31'),
  ('Драники', 110.9, 100006, '2016-05-31'),
  ('Оливье', 221.2, 100006, '2016-05-31'),
  ('Кофе', 50, 100006, '2018-05-31'),
  ('Картошка фри', 110.9, 100006, '2018-05-31'),
  ('Салат столичный', 221.2, 100006, '2018-05-31'),
  ('Сок апельсиновый', 50, 100006, '2018-09-30'),
  ('Гречка', 110.9, 100006, '2018-09-30'),
  ('Котлеты', 221.2, 100006, '2018-09-30'),
  ('Компот', 50, 100006, '2018-09-30'),
  ('Солянка', 500, 100007, '2017-10-11'),
  ('Овощной салат', 121.3, 100007, '2017-10-11'),
  ('Чай', 50, 100007, '2017-10-11'),
  ('Борщ', 500, 100007, '2017-10-12'),
  ('Печенка', 210.5, 100007, '2017-10-12'),
  ('Кисель', 50, 100007, '2017-10-12');

INSERT INTO votes ("user_id", "restaurant_id", "date") VALUES
  (100000, 100005, '2017-06-21'),
  (100000, 100006, '2018-05-31'),
  (100000, 100007, '2017-10-11'),
  (100001, 100006, '2018-05-31'),
  (100001, 100005, '2017-06-21'),
  (100002, 100006, '2018-09-30'),
  (100002, 100005, '2017-06-21'),
  (100002, 100007, '2017-10-12');
