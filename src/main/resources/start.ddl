CREATE TABLE `testing.users`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(45) NOT NULL,
    `enabled`  INT         NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `testing.authorities`
(
    `id`        int         NOT NULL AUTO_INCREMENT,
    `username`  varchar(45) NOT NULL,
    `authority` varchar(45) NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT
IGNORE INTO `testing.users` VALUES (NULL, 'happy', '12345', '1');
INSERT
IGNORE INTO `testing.authorities` VALUES (NULL, 'happy', 'write');

CREATE TABLE `testing.customer`
(
    `id`    int          NOT NULL AUTO_INCREMENT,
    `email` varchar(45)  NOT NULL,
    `pwd`   varchar(200) NOT NULL,
    `role`  varchar(45)  NOT NULL,
    PRIMARY KEY (`id`)
);

INSERT INTO `testing.customer` (`email`, `pwd`, `role`)
VALUES ('johndoe@example.com', '54321', 'admin');