package pl.put.backendoctodisco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.FlashcardListInfo;
import pl.put.backendoctodisco.repository.FlashcardListRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardListService {
    private final FlashcardListRepository repository;

    @Autowired
    public FlashcardListService(FlashcardListRepository repository) {
        this.repository = repository;
    }

    public FlashcardListInfo createFlashcardList(FlashcardListInfo flashcardListInfo){
        return repository.save(flashcardListInfo);
    }

    public Optional<FlashcardListInfo> findByName(Long userId, String name) {
        return repository.findByName(userId, name).stream().findFirst();
    }

    public List<FlashcardListInfo> findByName(String name) {
        return repository.findByName(name);
    }

}
