package pl.put.backendoctodisco.repository.test_repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.test_entity.TestTypeQuestion;

import java.util.List;

@Transactional
@Repository
public interface TestTypeRepository extends JpaRepository<TestTypeQuestion, Integer> {

    List<TestTypeQuestion> findByDifficulty(Integer difficulty);

}
