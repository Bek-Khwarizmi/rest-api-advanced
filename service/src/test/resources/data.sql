/* --------------filling in the table "tag"---------------------*/
INSERT INTO tag(name)
VALUES ('Tag 1');

INSERT INTO tag(name)
VALUES ('Tag 3');

INSERT INTO tag(name)
VALUES ('Tag 5');

INSERT INTO tag(name)
VALUES ('Tag 4');

INSERT INTO tag(name)
VALUES ('Tag 2');

/* --------------filling in the table "gift_certificate"---------------------*/
INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate 1', 'description 1', 10.1, 1, '2020-08-29T06:12:15', '2020-08-29T06:12:15');

INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate 3', 'description 3', 30.3, 3, '2019-08-29T06:12:15', '2019-08-29T06:12:15');

INSERT INTO gift_certificate(name, description, price, duration, create_date, last_update_date)
VALUES ('giftCertificate 2', 'description 2', 20.2, 2, '2018-08-29T06:12:15', '2018-08-29T06:12:15');

/* --------------filling in the table "gift_certificate_tags"---------------------*/
INSERT INTO gift_certificate_tags (gift_certificate_id, tag_id)
VALUES (1, 2);

INSERT INTO gift_certificate_tags (gift_certificate_id, tag_id)
VALUES (1, 5);

INSERT INTO gift_certificate_tags (gift_certificate_id, tag_id)
VALUES (2, 2);

INSERT INTO gift_certificate_tags (gift_certificate_id, tag_id)
VALUES (3, 3);

/* --------------filling in the table "users"---------------------*/
INSERT INTO users(first_name, last_name, phone_number, email)
VALUES ('Name 1', 'Surname 1', '+998901234567', 'name1_surname1@gmail.com');

INSERT INTO users(first_name, last_name, phone_number, email)
VALUES ('Name 2', 'Surname 2', '+998901234568', 'name2_surname2@gmail.com');

/* --------------filling in the table "orders"---------------------*/
INSERT INTO orders(price, created_date, user_id, gift_certificate_id)
VALUES (10.1, '2018-08-29T06:12:15', 1, 1);

INSERT INTO orders(price, created_date, user_id, gift_certificate_id)
VALUES (30.3, '2018-08-29T06:12:15', 1, 2);

INSERT INTO orders(price, created_date, user_id, gift_certificate_id)
VALUES (20.2, '2018-08-29T06:12:15', 2, 3);