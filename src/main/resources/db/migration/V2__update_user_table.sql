ALTER TABLE user
    ADD created_at datetime NULL;

ALTER TABLE user
    ADD updated_at datetime NULL;

ALTER TABLE user
    MODIFY email VARCHAR (100);

ALTER TABLE user
    MODIFY email VARCHAR (100) NOT NULL;

ALTER TABLE user
    MODIFY first_name VARCHAR (100);

ALTER TABLE user
    MODIFY first_name VARCHAR (100) NOT NULL;

ALTER TABLE user
    MODIFY last_name VARCHAR (100);

ALTER TABLE user
    MODIFY last_name VARCHAR (100) NOT NULL;

ALTER TABLE user
    MODIFY password VARCHAR (100);

ALTER TABLE user
    MODIFY password VARCHAR (100) NOT NULL;

ALTER TABLE user
    MODIFY phone_number VARCHAR (15);

ALTER TABLE user
    MODIFY phone_number VARCHAR (15) NOT NULL;