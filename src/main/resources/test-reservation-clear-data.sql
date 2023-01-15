DROP TABLE IF EXISTS theme;

CREATE TABLE theme
(
    id    bigint not null auto_increment,
    name  varchar(20),
    desc  varchar(255),
    price int,
    primary key (id)
);
