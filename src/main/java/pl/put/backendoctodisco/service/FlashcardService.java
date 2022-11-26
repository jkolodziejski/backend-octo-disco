package pl.put.backendoctodisco.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;
import pl.put.backendoctodisco.repository.FlashcardRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class FlashcardService {
    private final FlashcardRepository repository;


    public FlashcardService(FlashcardRepository repository) {
        this.repository = repository;
    }

    public Flashcard createFlashcard(Flashcard flashcard) {
        return repository.save(flashcard);
    }

    public Page<Flashcard> getAllFlashcards(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Flashcard> findById(Long id) {
        return repository.findById(id).stream().findFirst();
    }

    public List<Flashcard> findByWord(String word) {
        return repository.findByWord(word);
    }

    public List<Flashcard> findInUsersDictionary(User user, FlashcardRequest flashcardRequest) {
        List<Flashcard> foundFlashcards = findByWord(flashcardRequest.word);
        return foundFlashcards
                .stream().filter(card -> card.getIsGlobal() || Objects.equals(card.getUserId(), user.getId()))
                .filter(card ->
                        card.getWord().equals(flashcardRequest.word)
                                && card.getLanguage().equals(flashcardRequest.language)
                ).toList();
    }

    public Page<Flashcard> getAllFlashcardsGlobal(Pageable pageable, String language) {
        return repository.findFlashcardByIsGlobalTrueAndLanguage(pageable, language);
    }

    public Page<Flashcard> getFlashcardsUser(Long userId, Pageable pageable, String language) {
        return repository.findFlashcardByIsGlobalFalseAndUserIdAndLanguage(userId, pageable, language);
    }


    public Page<Flashcard> getFlashcardsByKeyword(Pageable pageable, String keyword, String language) {
        return repository.findAllUsersWithPagination(pageable, keyword, language);
    }


}
