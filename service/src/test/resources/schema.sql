-- -----------------------------------------------------
-- Table module3test.tag
-- -----------------------------------------------------
create table if not exists tag
(
    id   bigint auto_increment,
    name varchar(20) not null,
    primary key (id)
    );

-- -----------------------------------------------------
-- Table module3test.gift_certificate
-- -----------------------------------------------------
create table if not exists gift_certificate
(
    id               bigint auto_increment,
    name             varchar(45)  not null,
    description      text(300),
    price            decimal(8, 2)  not null,
    duration         smallint  not null,
    create_date      datetime               not null,
    last_update_date datetime               not null,
    primary key(id)
    );

-- -----------------------------------------------------
-- Table module3test.gift_certificates_tags
-- -----------------------------------------------------
create table if not exists gift_certificate_tags
(
    id                  bigint  auto_increment,
    gift_certificate_id bigint ,
    tag_id              bigint ,
    primary key (id),
    foreign key (gift_certificate_id) references gift_certificate (id),
    foreign key (tag_id) references tag (id)
    );

-- -----------------------------------------------------
-- Table module3test.users
-- -----------------------------------------------------
create table if not exists users
(
    id   bigint  auto_increment,
    first_name varchar(100) not null,
    last_name varchar(100) not null,
    phone_number varchar(100) not null,
    email varchar(100) not null,
    primary key(id)
    );

-- -----------------------------------------------------
-- Table module3test.order
-- -----------------------------------------------------
create table if not exists orders
(
    id                  bigint  auto_increment,
    price               decimal(8, 2)  not null,
    created_date        varchar(100)      not null ,
    user_id             bigint ,
    gift_certificate_id bigint ,
    primary key(id),
    foreign key(gift_certificate_id) references gift_certificate (id),
    foreign key(user_id) references users (id)
    );