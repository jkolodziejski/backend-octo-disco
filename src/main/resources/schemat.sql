CREATE TABLE USERS (
                       id int(11) NOT NULL AUTO_INCREMENT,
                       login varchar(50)  NOT NULL,
                       email varchar(50)  NOT NULL,
                       password varchar(200)  NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE KEY (email),
                       UNIQUE KEY (login)
);


CREATE TABLE WORD(
                     id int AUTO_INCREMENT PRIMARY KEY,
                     word varchar(255),
                     language varchar(100),
                     polishTranslation varchar(255)
);
ALTER TABLE WORD ADD CONSTRAINT chk_language CHECK(language in ('en', 'pl'));

CREATE TABLE ALIAS(
                      id int AUTO_INCREMENT PRIMARY KEY,
                      word_id int,
                      alias varchar(100),
                      CONSTRAINT fk_alias FOREIGN KEY(word_id) references flashcard(id)
);


