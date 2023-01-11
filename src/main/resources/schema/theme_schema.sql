DROP TABLE IF EXISTS THEME;

CREATE TABLE THEME
(
    id          bigint not null auto_increment,
    name        varchar(20),
    desc        varchar(255),
    price       int,
    primary key (id),
    unique (name)
);