CREATE TABLE product
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    title         VARCHAR(255)          NULL,
    `description` VARCHAR(255)          NULL,
    price         INT                   NOT NULL,
    stock         INT                   NOT NULL,
    CONSTRAINT pk_product PRIMARY KEY (id)
);