package pl.put.backendoctodisco.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.DifficultyLevel;
import pl.put.backendoctodisco.entity.Flashcard;

import java.util.List;

@Repository
public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel, Integer> {

    List<DifficultyLevel> findByLanguage(String language);
}
