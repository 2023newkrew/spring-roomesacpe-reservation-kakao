--DROP TABLE RESERVATION;

--CREATE TABLE RESERVATION
--(
--    id          bigint not null auto_increment,
--    date        date,
--    time        time,
--    name        varchar(20),
--    theme_name  varchar(20),
--    theme_desc  varchar(255),
--    theme_price int,
--    primary key (id)
--);
DROP TABLE IF EXISTS RESERVATION;

CREATE TABLE IF NOT EXISTS RESERVATION
(
    id          bigint not null auto_increment,
    date     date,
    time     time,
    name        varchar(20),
    theme_id bigint not null,
    primary key (id)
);

DROP TABLE IF EXISTS THEME;

CREATE TABLE IF NOT EXISTS THEME
(
    id    bigint not null auto_increment,
    name  varchar(20),
    desc  varchar(255),
    price int,
    primary key (id)
);

INSERT INTO THEME (name, desc, price) VALUES ('워너고홈', '병맛 어드벤처 회사 코믹물', 29000);