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

CREATE TABLE flashcard_list_info(
	id int AUTO_INCREMENT PRIMARY KEY,
	user_id int,
	name varchar(100),
	CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE flashcard_list_content(
    id int AUTO_INCREMENT PRIMARY KEY,
	flashcard_id int,
	list_id int,
	CONSTRAINT fk_flashcard FOREIGN KEY (flashcard_id) REFERENCES flashcard(id),
	CONSTRAINT fk_list FOREIGN KEY (list_id) REFERENCES flashcard_list_info(id)
);

ALTER TABLE WORD ADD CONSTRAINT chk_language CHECK(language in ('en', 'pl'));


