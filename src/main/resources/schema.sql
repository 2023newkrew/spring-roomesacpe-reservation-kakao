DROP TABLE IF EXISTS RESERVATION;
CREATE TABLE IF NOT EXISTS RESERVATION (
    id bigint not null auto_increment,
    date date,
    time time,
    name varchar(20),
    theme_name varchar(20),
    theme_desc varchar(20),
    theme_price int,
    primary key (id)
);