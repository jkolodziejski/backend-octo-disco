package pl.put.backendoctodisco.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.Flashcard;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

}
