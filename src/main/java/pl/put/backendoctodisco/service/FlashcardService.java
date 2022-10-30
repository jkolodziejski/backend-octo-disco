package pl.put.backendoctodisco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.repository.FlashcardRepository;

@Service
public class FlashcardService {
    private final FlashcardRepository repository;

    @Autowired
    public FlashcardService(FlashcardRepository repository) {
        this.repository = repository;
    }

    public Flashcard createFlashcard(Flashcard flashcard){
        return repository.save(flashcard);
    }
}
