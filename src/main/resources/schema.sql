CREATE TABLE IF NOT EXISTS RESERVATION
(
    id          bigint not null auto_increment, -- 방탈출 예약 번호
    date        date, -- 방탈출 예약 날짜
    time        time, -- 방탈출 예약 시간
    name        varchar(20), -- 방탈출 예약 이름
    theme_name  varchar(20), -- 예약된 방탈출 테마 이름
    theme_desc  varchar(255), -- 예약된 방탈출 테마 설명
    theme_price int, -- 예약된 방탈출 테마 가격
    primary key (id)
);