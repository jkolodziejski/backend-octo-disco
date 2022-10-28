package pl.put.backendoctodisco.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.exceptions.UserEmailAlreadyExistsException;
import pl.put.backendoctodisco.exceptions.UserLoginAlreadyExistsException;
import pl.put.backendoctodisco.repository.FlashcardRepository;
import pl.put.backendoctodisco.repository.UserRepository;

import java.util.List;


@Service
public class FlashcardServiceImpl{
    private final FlashcardRepository repository;

    @Autowired
    public FlashcardServiceImpl(UserRepository userRepository, FlashcardRepository repository) {
        this.repository = repository;
    }

    public Flashcard createFlashcard(Flashcard flashcard){
        return repository.save(flashcard);
    }
}
