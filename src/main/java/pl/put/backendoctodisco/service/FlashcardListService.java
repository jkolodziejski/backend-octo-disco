package pl.put.backendoctodisco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.FlashcardListInfo;
import pl.put.backendoctodisco.repository.FlashcardListRepository;

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

}
