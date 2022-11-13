package pl.put.backendoctodisco.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.FlashcardListInfo;
import pl.put.backendoctodisco.entity.User;

import java.util.List;

@Repository
public interface FlashcardListRepository extends JpaRepository<FlashcardListInfo, Integer> {

    @Query(value = "SELECT * FROM flashcard_list_info l WHERE l.user_id = ?1 AND l.name = ?2", nativeQuery = true)
    List<FlashcardListInfo> findByName(Long userId, String name);

    List<FlashcardListInfo> findByName(String name);
}
