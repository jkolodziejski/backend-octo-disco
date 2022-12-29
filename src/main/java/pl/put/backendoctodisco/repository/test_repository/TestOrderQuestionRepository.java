package pl.put.backendoctodisco.repository.test_repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.test_entity.TestOrderQuestion;

import java.util.List;

@Transactional
@Repository
public interface TestOrderQuestionRepository extends JpaRepository<TestOrderQuestion, Integer> {

    @Query(value = "SELECT * FROM test_order_question l WHERE l.difficulty_id = ?1", nativeQuery = true)
    List<TestOrderQuestion> findByDifficultyId(Long difficulty_id);

}
