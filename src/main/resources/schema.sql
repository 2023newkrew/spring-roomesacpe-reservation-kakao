CREATE TABLE reservation
(
    id          bigint not null auto_increment comment '예약ID',
    date     date comment '예약날짜',
    time     time comment '예약시간',
    name        varchar(20) comment '예약자이름',
    theme_id bigint not null comment '테마ID',
    primary key (id)
);

CREATE TABLE theme
(
    id    bigint not null auto_increment comment '테마ID',
    name  varchar(20) comment '테마이름',
    desc  varchar(255) comment '테마설명',
    price int comment '테마가격',
    primary key (id)
);