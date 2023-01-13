CREATE TABLE IF NOT EXISTS reservation
(
    id       bigint not null auto_increment,
    datetime datetime,
    name     varchar(20),
    theme_id bigint not null,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS theme
(
    id    bigint not null auto_increment,
    name  varchar(20),
    desc  varchar(255),
    price int,
    primary key (id)
);

CREATE INDEX ON reservation(datetime);
CREATE INDEX ON theme(name);

alter table reservation add constraint datetime_unique unique(datetime);
alter table theme add constraint name_unique unique(name);
