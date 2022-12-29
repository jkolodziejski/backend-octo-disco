package pl.put.backendoctodisco.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.responses.SingleTranslation;

import java.util.List;

@Repository
public interface SingleTranslationRepository extends JpaRepository<SingleTranslation, Integer> {

    @Query(value = "SELECT COUNT(flashcard.user_id) as popularity, LOWER(flashcard.word) as word, LOWER(alias.alias) as translation\n" +
            "FROM flashcard, alias\n" +
            "WHERE flashcard.id = alias.word_id\n" +
            "AND flashcard.is_global = 0\n" +
            "AND flashcard.language=?1\n" +
            "GROUP BY LOWER(flashcard.word), LOWER(alias.alias)\n" +
            "HAVING popularity >= ?2\n" +
            "ORDER BY COUNT(flashcard.user_id) DESC", nativeQuery = true)
    List<SingleTranslation> findPopularTranslations(String language, Integer popularity);

    @Query(value = "SELECT popularity, lcl.word, lcl.translation\n" +
            "FROM (\n" +
            "\tSELECT COUNT(flashcard.user_id) as popularity, LOWER(flashcard.word) as word, LOWER(alias.alias) as translation\n" +
            "\tFROM flashcard, alias\n" +
            "\tWHERE flashcard.id = alias.word_id\n" +
            "\tAND flashcard.is_global = 0\n" +
            "\tAND flashcard.language=?1\n" +
            "\tGROUP BY LOWER(flashcard.word), LOWER(alias.alias)\n" +
            "\tHAVING popularity >= ?2\n" +
            "\tORDER BY COUNT(flashcard.user_id) DESC\n" +
            "\t) lcl\n" +
            "LEFT JOIN\n" +
            "\t(SELECT flashcard.word, alias.alias FROM flashcard, alias WHERE flashcard.id = alias.word_id AND flashcard.is_global = 1 AND flashcard.language=?1) glbl\n" +
            "ON lcl.word = glbl.word AND glbl.alias = lcl.translation\n" +
            "WHERE glbl.word IS NULL", nativeQuery = true)
    List<SingleTranslation> findFilteredPopularTranslations(String language, Integer popularity);
}
