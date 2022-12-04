package pl.put.backendoctodisco.repository.test_repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.test_entity.TestChooseQuestion;
import pl.put.backendoctodisco.entity.test_entity.TestTypeQuestion;

import java.util.List;

@Transactional
@Repository
public interface TestChooseRepository extends JpaRepository<TestChooseQuestion, Integer> {

    List<TestChooseQuestion> findByDifficulty(Integer difficulty);

}
