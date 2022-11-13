package pl.put.backendoctodisco.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    List<Flashcard> findByWord(String word);

    Page<Flashcard> findFlashcardByIsGlobalTrue(Pageable pageable);

    Page<Flashcard> findFlashcardByIsGlobalFalseAndUserId(Long userId, Pageable pageable);

}
