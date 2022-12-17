package pl.put.backendoctodisco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.FlashcardStatistics;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface StatsQuizRepository extends JpaRepository<FlashcardStatistics, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE flashcard_statistics set learned = ?3 where user_id = ?1 and flashcard_id = ?2", nativeQuery = true)
    int updateCard(Long user_id, Long flashcard_id, Boolean learned);

    @Query(value = "SELECT * FROM flashcard_statistics where user_id = ?1 and flashcard_id = ?2", nativeQuery = true)
    Optional<FlashcardStatistics> findByUserIdAndFlashcardId(Long user_id, Long flashcard_id);

}
