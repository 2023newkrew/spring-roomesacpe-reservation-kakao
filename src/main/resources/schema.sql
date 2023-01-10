DROP TABLE IF EXISTS RESERVATION;

DROP TABLE IF EXISTS THEME;

CREATE TABLE RESERVATION
(
    id          bigint not null auto_increment,
    date        date,
    time        time,
    name        varchar(20),
    theme_name  varchar(20),
    theme_desc  varchar(255),
    theme_price int,
    primary key (id),
    unique (date, time)
);

CREATE TABLE THEME
(
  id          bigint not null auto_increment,
  name        varchar(20),
  desc        varchar(255),
  price       int,
  primary key (id)
);


