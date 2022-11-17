package pl.put.backendoctodisco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.repository.FlashcardRepository;

import java.util.List;


@Service
public class FlashcardService {
    private final FlashcardRepository repository;


    public FlashcardService(FlashcardRepository repository) {
        this.repository = repository;
    }

    public Flashcard createFlashcard(Flashcard flashcard){
        return repository.save(flashcard);
    }

    public List<Flashcard> getAllFlashcards(Pageable pageable){
        Page<Flashcard> page =repository.findAll(pageable);
        return   page.getContent();
    }

    public List<Flashcard> findByWord(String word) {
        return  repository.findByWord(word);
    }

    public List<Flashcard> getAllFlashcardsGlobal(Pageable pageable){
        Page<Flashcard> page = repository.findFlashcardByIsGlobalTrue(pageable);
        return page.getContent();
    }

    public List<Flashcard> getFlashcardsUser(Long user_id, Pageable pageable){
        Page<Flashcard> page = repository.findFlashcardByIsGlobalFalseAndUserId(user_id,pageable);
        return  page.getContent();
    }

}
