CREATE TABLE IF NOT EXISTS RESERVATION
(
    id          bigint not null auto_increment, -- 방탈출 예약 번호
    date        date, -- 방탈출 예약 날짜
    time        time, -- 방탈출 예약 시간
    name        varchar(20), -- 방탈출 예약 이름
    theme_id bigint not null, -- 예약된 방탈출 테마 아이디
    primary key (id)
);

CREATE TABLE IF NOT EXISTS THEME
(
    id    bigint not null auto_increment, -- 방탈출 테마 번호
    name  varchar(20), -- 방탈출 테마 이름
    desc  varchar(255), -- 방탈출 테마 설명
    price int, -- 방탈출 테마 가격
    primary key (id)
);