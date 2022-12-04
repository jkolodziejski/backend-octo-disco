package pl.put.backendoctodisco.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.Flashcard;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    static final String countQuery = "SELECT count(DISTINCT f.id) from flashcard f join alias a on f.id = a.word_id where (word like concat('%',?1,'%') or alias like concat('%',?1,'%')) and language = ?2";
    static final String searchQuery = "SELECT DISTINCT f.* from flashcard f join alias a on f.id = a.word_id where (word like concat('%',?1,'%') or alias like concat('%',?1,'%')) and language  = ?2";

    List<Flashcard> findByWord(String word);

    Page<Flashcard> findFlashcardByIsGlobalTrueAndLanguage(Pageable pageable, String language);

    Page<Flashcard> findFlashcardByIsGlobalFalseAndUserIdAndLanguage(Long userId, Pageable pageable, String language);

    List<Flashcard> findById(Long id);

    @Query(countQuery = countQuery+" and is_global = 1",
            value = searchQuery+" and is_global = 1",
            nativeQuery = true)
    Page<Flashcard> searchGlobalFlashcardsWithPagination(Pageable pageable, String keyword, String language);

    @Query(countQuery = countQuery+" and is_global = 0 and user_id = ?3",
            value = searchQuery+" and is_global = 0 and user_id = ?3",
            nativeQuery = true)
    Page<Flashcard> searchUsersFlashcardsWithPagination(Pageable pageable, String keyword, String language, Long userId);

    @Query(countQuery = countQuery+" and ((is_global = 0 and user_id = ?3) or is_global = 1)",
            value = searchQuery+" and ((is_global = 0 and user_id = ?3) or is_global = 1)",
            nativeQuery = true)
    Page<Flashcard> searchFlashcardsWithPagination(Pageable pageable, String keyword, String language, Long userId);
}
