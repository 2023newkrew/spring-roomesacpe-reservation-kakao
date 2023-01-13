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