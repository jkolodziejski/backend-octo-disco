package pl.put.backendoctodisco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.repository.FlashcardRepository;

import java.util.List;
import java.util.Optional;


@Service
public class FlashcardService {
    private final FlashcardRepository repository;


    public FlashcardService(FlashcardRepository repository) {
        this.repository = repository;
    }

    public Flashcard createFlashcard(Flashcard flashcard){
        return repository.save(flashcard);
    }

    public Page<Flashcard> getAllFlashcards(Pageable pageable){
        return   repository.findAll(pageable);
    }

    public Optional<Flashcard> findById(Long id) {
        return repository.findById(id).stream().findFirst();
    }

    public List<Flashcard> findByWord(String word) {
        return  repository.findByWord(word);
    }

    public Page<Flashcard> getAllFlashcardsGlobal(Pageable pageable){
        return repository.findFlashcardByIsGlobalTrue(pageable);
    }

    public Page<Flashcard> getFlashcardsUser(Long user_id, Pageable pageable){
        return  repository.findFlashcardByIsGlobalFalseAndUserId(user_id,pageable);
    }


    public Page<Flashcard> getFlashcardsByKyeword(Pageable pageable,String keyword){
        return repository.findAllUsersWithPagination(pageable,keyword);

    }


}
