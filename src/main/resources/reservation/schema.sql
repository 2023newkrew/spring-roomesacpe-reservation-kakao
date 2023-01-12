DROP TABLE reservation IF EXISTS;
DROP TABLE theme IF EXISTS;

CREATE TABLE theme
(
    id      bigint not null auto_increment,
    name    varchar(20),
    desc    varchar(255),
    price   int,
    primary key (id),
    unique (name)
);

CREATE TABLE reservation
(
    id          bigint not null auto_increment,
    date        date,
    time        time,
    name        varchar(20),
    theme_id    bigint not null,
    primary key (id),
    foreign key (theme_id) references theme (id),
    unique (date, time, theme_id)
);