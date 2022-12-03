package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.entity.responses.TestResponse;
import pl.put.backendoctodisco.repository.AliasRepository;
import pl.put.backendoctodisco.repository.FlashcardListContentRepository;
import pl.put.backendoctodisco.repository.FlashcardRepository;

import java.util.List;


@Service
public class TestService {
    private final TestResponse repository;

    public TestService(FlashcardRepository repository, FlashcardListContentRepository contentRepository, AliasRepository aliasRepository) {
        this.repository = repository;
        this.contentRepository = contentRepository;
        this.aliasRepository = aliasRepository;
    }

    public TestResponse createTest(List<FlashcardResponse> flashcards){


        return new TestResponse(questions);
    }


}
