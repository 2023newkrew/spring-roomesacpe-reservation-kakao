CREATE TABLE RESERVATION
(
    id          bigint not null auto_increment,
    date        date,
    time        time,
    name        varchar(20),
    theme_name  varchar(20),
    theme_desc  varchar(255),
    theme_price int,
    primary key (id)
);

CREATE TABLE theme
(
    id    bigint not null auto_increment,
    name  varchar(20),
    desc  varchar(255),
    price int,
    primary key (id)
);
