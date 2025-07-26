CREATE TABLE user
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    email        VARCHAR(255) NULL,
    password     VARCHAR(255) NULL,
    first_name   VARCHAR(255) NULL,
    last_name    VARCHAR(255) NULL,
    phone_number VARCHAR(255) NULL,
    address      VARCHAR(255) NULL,
    birth_date   VARCHAR(255) NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE UNIQUE INDEX idx_user_email ON user (email);
CREATE UNIQUE INDEX idx_user_phone_number ON user (phone_number);