CREATE TABLE IF NOT EXISTS RESERVATION (
    id          bigint not null auto_increment,
    date     date,
    time     time,
    name        varchar(20),
    theme_id bigint not null,
    primary key (id)
);