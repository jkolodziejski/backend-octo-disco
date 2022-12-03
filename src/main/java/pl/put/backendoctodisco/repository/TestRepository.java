package pl.put.backendoctodisco.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.responses.TestResponse;

import java.util.List;

@Transactional
@Repository
public interface TestTypeRepository extends JpaRepository<TestTypeQuestion, Integer> {

    List<TestTypeQuestion> findByLevel(Long level);

}
