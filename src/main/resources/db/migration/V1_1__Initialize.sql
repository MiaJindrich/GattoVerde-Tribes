CREATE TABLE `kingdoms`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) DEFAULT NULL,
    `location_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `locations`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `xcord`      int    NOT NULL,
    `ycord`      int    NOT NULL,
    `kingdom_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (kingdom_id) REFERENCES kingdoms (id)
);

CREATE TABLE `buildings`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT,
    `type`        varchar(31) NOT NULL,
    `finished_at` bigint DEFAULT NULL,
    `level`       int      DEFAULT NULL,
    `hp`          int      DEFAULT NULL,
    `started_at`  bigint DEFAULT NULL,
    `kingdom_id`  bigint   DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (kingdom_id) REFERENCES kingdoms (id)
);

CREATE TABLE `players`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `avatar`     varchar(255) DEFAULT NULL,
    `password`   varchar(255) DEFAULT NULL,
    `points`     int          DEFAULT NULL,
    `username`   varchar(255) DEFAULT NULL,
    `kingdom_id` bigint       DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (kingdom_id) REFERENCES kingdoms (id)
);

CREATE TABLE `resources`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `type`       varchar(31) NOT NULL,
    `amount`     int DEFAULT NULL,
    `kingdom_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (kingdom_id) REFERENCES kingdoms (id)
);

CREATE TABLE `troops`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT,
    `type`        varchar(31) NOT NULL,
    `attack`      int         NOT NULL,
    `defense`     int         NOT NULL,
    `finished_at` bigint DEFAULT NULL,
    `level`       int         NOT NULL,
    `hp`          int         NOT NULL,
    `started_at`  bigint DEFAULT NULL,
    `kingdom_id`  bigint DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (kingdom_id) REFERENCES kingdoms (id)
);

CREATE TABLE `game_time`
(
    `id`           bigint NOT NULL AUTO_INCREMENT,
    `server_start` bigint DEFAULT NULL,
    `ticks`        bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE kingdoms ADD FOREIGN KEY (location_id) REFERENCES locations (id);