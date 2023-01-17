CREATE TABLE IF NOT EXISTS RESERVATION
(
    id          bigint not null auto_increment,
    date     date not null,
    time     time not null,
    name        varchar(20) not null,
    theme_id bigint not null,
    primary key (id)
    );

CREATE TABLE IF NOT EXISTS theme
(
    id    bigint not null auto_increment,
    name  varchar(20) not null,
    desc  varchar(255) not null,
    price int not null,
    primary key (id)
    );