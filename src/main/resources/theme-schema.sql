CREATE TABLE IF NOT EXISTS theme (
    id    bigint not null auto_increment,
    name  varchar(20),
    desc  varchar(255),
    price int,
    primary key (id)
);