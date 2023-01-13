DROP TABLE IF EXISTS RESERVATION;
CREATE TABLE RESERVATION(
    id bigint not null auto_increment,
    date date,
    time time,
    name varchar(20),
    theme_name varchar(20),
    theme_desc varchar(20),
    theme_price int,
    primary key (id)
);
INSERT INTO reservation (date, time, name, theme_name, theme_desc, theme_price)
VALUES ('2022-08-01', '13:00', 'test', '워너고홈', '병맛 어드벤처 회사 코믹물', 29000);
