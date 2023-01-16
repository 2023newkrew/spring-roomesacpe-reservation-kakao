DROP TABLE IF EXISTS THEME CASCADE;

CREATE TABLE THEME
(
    id          bigint not null auto_increment,
    name        varchar(20),
    desc        varchar(255),
    price       int,
    primary key (id)
);

DROP TABLE IF EXISTS RESERVATION CASCADE;

CREATE TABLE RESERVATION
(
    id          bigint not null auto_increment,
    date        date,
    time        time,
    name        varchar(20),
    theme_id    bigint,
    primary key (id)
);