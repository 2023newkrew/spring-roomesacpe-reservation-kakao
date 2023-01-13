DROP TABLE IF EXISTS RESERVATION;
DROP TABLE IF EXISTS THEME;

CREATE TABLE RESERVATION
(
    id       bigint not null auto_increment,
    date     date,
    time     time,
    name     varchar(20),
    theme_id bigint not null,
    primary key (id)
);

CREATE TABLE THEME
(
    id    bigint not null auto_increment,
    name  varchar(20),
    desc  varchar(255),
    price int,
    primary key (id)
);

ALTER TABLE RESERVATION
    ADD FOREIGN KEY (theme_id)
    REFERENCES THEME (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

INSERT INTO THEME(name, desc, price) VALUES ('워너고홈', '병맛 어드벤처 회사 코믹물', 29000);
INSERT INTO THEME(name, desc, price) VALUES ('넥스트스텝', '아찔한 개발여행', 99000);
INSERT INTO THEME(name, desc, price) VALUES ('가', '나다라마바사', 100);
