package pl.put.backendoctodisco.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    List<Flashcard> findByWord(String word);

    Page<Flashcard> findFlashcardByIsGlobalTrue(Pageable pageable);

    Page<Flashcard> findFlashcardByIsGlobalFalseAndUserId(Long userId, Pageable pageable);

    List<Flashcard> findById(Long id);

    @Query(value = "SELECT DISTINCT f.* from flashcard f join alias a on f.id = a.word_id where word like concat('%',?1,'%') or alias like  concat('%',?1,'%') ",
            countQuery = "SELECT DISTINCT count(*) from flashcard f join alias a on f.id = a.word_id where word like concat('%',?1,'%') or alias like  concat('%',?1,'%') ",
            nativeQuery = true)
    Page<Flashcard> findAllUsersWithPagination(Pageable pageable, String keyword);


}
