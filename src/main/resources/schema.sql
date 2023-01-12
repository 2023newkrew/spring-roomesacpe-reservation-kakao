drop table if exists `RESERVATION` CASCADE;
drop table if exists `THEME` CASCADE;

CREATE TABLE `THEME`
(
    `id`  BIGINT NOT NULL AUTO_INCREMENT,
    `name`  varchar(20) not null,
    `desc`  varchar(255),
    `price` int not null,
    PRIMARY KEY (id),
    UNIQUE (NAME)
);

CREATE TABLE `RESERVATION`
(
    `id`          bigint not null auto_increment,
    `date`        date not null,
    `time`        time not null,
    `name`        varchar(20) not null,
    `theme_id`    bigint not null,
    primary key (id),
    FOREIGN KEY (theme_id) REFERENCES THEME(id),
    UNIQUE(date, time, theme_id)
);


