DROP TABLE IF EXISTS THEME;
DROP TABLE IF EXISTS RESERVATION;

CREATE TABLE RESERVATION
(
    id    bigint not null auto_increment,
    date  date,
    time  time,
    name  varchar(20),
    theme_id  bigint not null,
    primary key (id)
);

CREATE TABLE theme
(
    id    bigint not null auto_increment,
    name  varchar(20),
    desc  varchar(255),
    price int,
    primary key (id)
);

INSERT INTO THEME VALUES (100, 'themeName', 'description of theme', 50000);
INSERT INTO THEME VALUES (101, 'themeCanDelete', 'theme with no reservation', 30000);
INSERT INTO RESERVATION VALUES (100, '2024-01-01', '09:30', 'name', 100);
