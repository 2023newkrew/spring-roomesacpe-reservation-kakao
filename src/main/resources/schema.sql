CREATE TABLE reservation
(
    id          BIGINT NOT NULL AUTO_INCREMENT,
    date        DATE,
    time        TIME,
    name        VARCHAR(20),
    theme_name  VARCHAR(20),
    theme_desc  VARCHAR(255),
    theme_price INT,
    PRIMARY KEY (id)
);
CREATE TABLE theme
(
    id    BIGINT NOT NULL AUTO_INCREMENT,
    name  VARCHAR(20),
    desc  VARCHAR(255),
    price INT,
    PRIMARY KEY (id)
);