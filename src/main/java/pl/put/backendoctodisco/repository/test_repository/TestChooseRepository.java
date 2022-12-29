package pl.put.backendoctodisco.repository.test_repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.test_entity.TestChooseQuestion;

import java.util.List;

@Transactional
@Repository
public interface TestChooseRepository extends JpaRepository<TestChooseQuestion, Integer> {

    @Query(value = "SELECT * FROM test_choose_question l WHERE l.difficulty_id = ?1", nativeQuery = true)
    List<TestChooseQuestion> findByDifficultyId(Long difficulty_id);

}
