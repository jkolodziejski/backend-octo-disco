package pl.put.backendoctodisco.repository;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;
import pl.put.backendoctodisco.entity.Flashcard;

public interface FlashcardRepository extends JpaRepository<Flashcard,Integer> {

=======

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;

import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {
>>>>>>> feature/#48-creating-flashcards

}
