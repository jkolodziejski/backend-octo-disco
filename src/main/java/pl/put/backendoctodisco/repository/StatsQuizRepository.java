package pl.put.backendoctodisco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.Alias;
import pl.put.backendoctodisco.entity.FlashcardStatistics;

import java.util.List;

@Repository
public interface StatsQuizRepository extends JpaRepository<FlashcardStatistics, Integer> {

    @Modifying
    @Query("update User u set u.authToken = ?1 where u.id = ?2")
    void updateStatsByCardAndUser(Long user_id, Long flashcard_id);

}
