package pl.put.backendoctodisco.repository.test_repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.test_entity.TestChooseQuestion;
import pl.put.backendoctodisco.entity.test_entity.TestOrderQuestion;

import java.util.List;

@Transactional
@Repository
public interface TestOrderQuestionRepository extends JpaRepository<TestOrderQuestion, Integer> {

    List<TestOrderQuestion> findByDifficulty(Integer difficulty);

}
