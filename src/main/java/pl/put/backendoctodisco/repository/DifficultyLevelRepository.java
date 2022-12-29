package pl.put.backendoctodisco.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.put.backendoctodisco.entity.DifficultyLevel;
import pl.put.backendoctodisco.entity.FlashcardListInfo;
import pl.put.backendoctodisco.entity.User;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel, Integer> {

    List<DifficultyLevel> findByLanguage(String language);

    Optional<DifficultyLevel> findById(Long id);

}
