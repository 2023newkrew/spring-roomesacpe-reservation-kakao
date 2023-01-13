DROP TABLE IF EXISTS RESERVATION;
DROP TABLE IF EXISTS THEME;
CREATE TABLE RESERVATION(
                            id          bigint not null auto_increment,
                            date     date,
                            time     time,
                            name        varchar(20),
                            theme_id bigint not null,
                            primary key (id)
);
CREATE TABLE THEME(
                      id    bigint not null auto_increment,
                      name  varchar(20),
                      desc  varchar(255),
                      price int,
                      primary key (id)
);
INSERT INTO THEME (name, desc, price)
VaLUES ('워너고홈', '병맛 어드벤처 회사 코믹물', 29000);
INSERT INTO reservation (date, time, name, theme_id)
VALUES ('2022-08-01', '13:00', 'test', 1);
