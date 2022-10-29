package pl.put.backendoctodisco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.put.backendoctodisco.entity.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard,Integer> {


}
