package pl.put.backendoctodisco.repository.test_repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.test_entity.TestTypeQuestion;

import java.util.List;

@Transactional
@Repository
public interface TestTypeRepository extends JpaRepository<TestTypeQuestion, Integer> {

    @Query(value = "SELECT * FROM test_type_question l WHERE l.difficulty_id = ?1", nativeQuery = true)
    List<TestTypeQuestion> findByDifficultyId(Long difficulty_id);

}
