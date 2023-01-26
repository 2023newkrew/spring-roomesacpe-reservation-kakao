DROP TABLE IF EXISTS THEME CASCADE;

CREATE TABLE THEME
(
    id          bigint not null auto_increment  comment '테마 ID',
    name        varchar(20)                     comment '테마 이름',
    desc        varchar(255)                    comment '테마 설명',
    price       int                             comment '테마 가격',
    primary key (id)
);

DROP TABLE IF EXISTS RESERVATION CASCADE;

CREATE TABLE RESERVATION
(
    id          bigint not null auto_increment  comment '예약 ID',
    date        date                            comment '예약일',
    time        time                            comment '예약시간',
    name        varchar(20)                     comment '예약자명',
    theme_id    bigint                          comment '테마 ID',
    primary key (id)
);