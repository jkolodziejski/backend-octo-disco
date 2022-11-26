package pl.put.backendoctodisco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.FlashcardListContent;

import java.util.List;

@Repository
public interface FlashcardListContentRepository extends JpaRepository<FlashcardListContent, Integer> {

    @Query(value = "SELECT * FROM flashcard_list_content l WHERE l.flashcard_id = ?1 AND l.list_id = ?2", nativeQuery = true)
    List<FlashcardListContent> findCardInList(Long flashcardId, Long listId);

    List<FlashcardListContent> findByListId(Long listId);
}
