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

CREATE TABLE ALIAS(
                      id int AUTO_INCREMENT PRIMARY KEY,
                      word_id int,
                      alias varchar(100),
                      CONSTRAINT fk_alias FOREIGN KEY(word_id) references flashcard(id)
);

CREATE TABLE test_type_question(
	id int AUTO_INCREMENT PRIMARY KEY,
	language varchar(100),
	difficulty int,
	sentence varchar(255),
	sentence_word varchar(100),
	translation varchar(255),
	translation_word varchar(100)
);

CREATE TABLE test_choose_question(
	id int AUTO_INCREMENT PRIMARY KEY,
	language varchar(100),
	difficulty int,
	sentence varchar(255),
	answer varchar(100),
	false_option_1 varchar(100),
	false_option_2 varchar(100),
	false_option_3 varchar(100),
	false_option_4 varchar(100)
);

CREATE TABLE test_order_question(
	id int AUTO_INCREMENT PRIMARY KEY,
	language varchar(100),
	difficulty int,
	sentence varchar(255)
);

CREATE TABLE test_order_answer(
	id int AUTO_INCREMENT PRIMARY KEY,
	question_id int,
	order_nr int,
	sentence varchar(100),
	CONSTRAINT fk_answer FOREIGN KEY(question_id) references test_order_question(id)
);

CREATE TABLE flashcard_statistics(
	id int AUTO_INCREMENT PRIMARY KEY,
	flashcard_id int,
	user_id int,
	learned number(1),
	CONSTRAINT fk_flashcard FOREIGN KEY(flashcard_id) references flashcard(id) ON DELETE CASCADE
	CONSTRAINT fk_user FOREIGN KEY(user_id) references user(id) ON DELETE CASCADE
);


