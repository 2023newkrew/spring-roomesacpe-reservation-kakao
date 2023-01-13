CREATE TABLE reservation
(
    id          bigint not null auto_increment comment='시스템 식별번호',
    date        date comment='예약일자',
    time        time comment='예약시간',
    name        varchar(20) comment='예약자 이름',
    theme_name  varchar(20) comment='테마 이름',
    theme_desc  varchar(255) comment='테마 소개',
    theme_price int comment='테마 가격',
    primary key (id)
) comment='방탈출 예약자 정보';