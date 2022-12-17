package pl.put.backendoctodisco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.FlashcardStatistics;
import pl.put.backendoctodisco.entity.TestStatistics;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface StatsTestRepository extends JpaRepository<TestStatistics, Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE test_statistics set learned = ?4 where user_id = ?1 and question_id = ?2 and question_type = ?3", nativeQuery = true)
    int updateTest(Long user_id, Long question_id, String type, Boolean learned);

    @Query(value = "SELECT * FROM test_statistics where user_id = ?1 and question_id = ?2 and question_type = ?3", nativeQuery = true)
    Optional<TestStatistics> findByUserIdAndQuestionId(Long user_id, Long question_id, String type);

}
