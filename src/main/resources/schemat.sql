
CREATE TABLE `user` (
                        `id` int(11) NOT NULL AUTO_INCREMENT,
                        `login` varchar(50) NOT NULL,
                        `email` varchar(50) NOT NULL,
                        `password` varchar(200) NOT NULL,
                        `auth_token` varchar(500) DEFAULT NULL,
                        `permissions` varchar(50) DEFAULT NULL,
                        `exp` int(11) DEFAULT 0,
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `email` (`email`),
                        UNIQUE KEY `login` (`login`)
);

CREATE TABLE `flashcard` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `word` varchar(255) NOT NULL,
                             `language` varchar(100) NOT NULL,
                             `is_global` tinyint(1) NOT NULL DEFAULT 0,
                             `user_id` int(11) NOT NULL,
                             PRIMARY KEY (`id`),
                             KEY `fk_flashcard_user` (`user_id`),
                             CONSTRAINT `fk_flashcard_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `alias` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `word_id` int(11) DEFAULT NULL,
                         `alias` varchar(100) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `fk_alias_flashcard` (`word_id`),
                         CONSTRAINT `fk_alias_flashcard` FOREIGN KEY (`word_id`) REFERENCES `flashcard` (`id`) ON DELETE CASCADE
) ;

CREATE TABLE `flashcard_list_info` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `user_id` int(11) DEFAULT NULL,
                                       `name` varchar(100) DEFAULT NULL,
                                       `description` varchar(500) DEFAULT NULL,
                                       PRIMARY KEY (`id`),
                                       KEY `fk_user` (`user_id`),
                                       CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `flashcard_list_content` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `flashcard_id` int(11) DEFAULT NULL,
                                          `list_id` int(11) DEFAULT NULL,
                                          PRIMARY KEY (`id`),
                                          KEY `fk_flashcardlist_flashcard` (`flashcard_id`),
                                          KEY `fk_listcontent_listinfo` (`list_id`),
                                          CONSTRAINT `fk_flashcardlist_flashcard` FOREIGN KEY (`flashcard_id`) REFERENCES `flashcard` (`id`) ON DELETE CASCADE,
                                          CONSTRAINT `fk_listcontent_listinfo` FOREIGN KEY (`list_id`) REFERENCES `flashcard_list_info` (`id`) ON DELETE CASCADE
) ;

CREATE TABLE `test_statistics` (
                                   `id` int(11) NOT NULL AUTO_INCREMENT,
                                   `question_id` int(11) DEFAULT NULL,
                                   `question_type` varchar(10) DEFAULT NULL,
                                   `user_id` int(11) DEFAULT NULL,
                                   `learned` tinyint(1) DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `fk_teststats_user` (`user_id`),
                                   CONSTRAINT `fk_teststats_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE,
                                   CONSTRAINT `chk_question_type` CHECK (`question_type` in ('type','choose','order'))
);


CREATE TABLE `flashcard_statistics` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `flashcard_id` int(11) DEFAULT NULL,
                                        `user_id` int(11) DEFAULT NULL,
                                        `learned` tinyint(1) DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `fk_stats_flashcard` (`flashcard_id`),
                                        KEY `fk_stats_user` (`user_id`),
                                        CONSTRAINT `fk_stats_flashcard` FOREIGN KEY (`flashcard_id`) REFERENCES `flashcard` (`id`) ON DELETE CASCADE,
                                        CONSTRAINT `fk_stats_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
);


CREATE TABLE `difficulty_level` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `language` varchar(100) DEFAULT NULL,
                                    `name` varchar(100) DEFAULT NULL,
                                    `difficulty` int(11) DEFAULT NULL,
                                    `exp_needed` int(11) DEFAULT NULL,
                                    PRIMARY KEY (`id`),
                                    CONSTRAINT `chk_test_language` CHECK (`language` = 'en')
) ;


CREATE TABLE `test_choose_question` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `difficulty_id` int(11) DEFAULT NULL,
                                        `sentence` varchar(255) DEFAULT NULL,
                                        `answer` varchar(100) DEFAULT NULL,
                                        `false_option_1` varchar(100) DEFAULT NULL,
                                        `false_option_2` varchar(100) DEFAULT NULL,
                                        `false_option_3` varchar(100) DEFAULT NULL,
                                        `false_option_4` varchar(100) DEFAULT NULL,
                                        PRIMARY KEY (`id`),
                                        KEY `fk_chooseq_difficulty` (`difficulty_id`),
                                        CONSTRAINT `fk_chooseq_difficulty` FOREIGN KEY (`difficulty_id`) REFERENCES `difficulty_level` (`id`)
) ;

CREATE TABLE `test_type_question` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `difficulty_id` int(11) DEFAULT NULL,
                                      `sentence` varchar(255) DEFAULT NULL,
                                      `sentence_word` varchar(100) DEFAULT NULL,
                                      `translation` varchar(255) DEFAULT NULL,
                                      `translation_word` varchar(100) DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      KEY `fk_typeq_difficulty` (`difficulty_id`),
                                      CONSTRAINT `fk_typeq_difficulty` FOREIGN KEY (`difficulty_id`) REFERENCES `difficulty_level` (`id`)
);


CREATE TABLE `test_type_question` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `difficulty_id` int(11) DEFAULT NULL,
                                      `sentence` varchar(255) DEFAULT NULL,
                                      `sentence_word` varchar(100) DEFAULT NULL,
                                      `translation` varchar(255) DEFAULT NULL,
                                      `translation_word` varchar(100) DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      KEY `fk_typeq_difficulty` (`difficulty_id`),
                                      CONSTRAINT `fk_typeq_difficulty` FOREIGN KEY (`difficulty_id`) REFERENCES `difficulty_level` (`id`)
);

CREATE TABLE `test_order_answer` (
                                     `id` int(11) NOT NULL AUTO_INCREMENT,
                                     `question_id` int(11) DEFAULT NULL,
                                     `order_nr` int(11) DEFAULT NULL,
                                     `sentence` varchar(100) DEFAULT NULL,
                                     PRIMARY KEY (`id`),
                                     KEY `fk_answer` (`question_id`),
                                     CONSTRAINT `fk_answer` FOREIGN KEY (`question_id`) REFERENCES `test_order_question` (`id`)
) ;