package pl.put.backendoctodisco.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.*;
import pl.put.backendoctodisco.entity.requests.FlashcardRequest;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.entity.responses.SingleTranslation;
import pl.put.backendoctodisco.repository.*;
import pl.put.backendoctodisco.utils.DictionaryChoice;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class FlashcardService {
    private final FlashcardRepository repository;
    private final FlashcardListContentRepository contentRepository;
    private final AliasRepository aliasRepository;
    private final StatsQuizRepository quizStatsRepository;
    private final SingleTranslationRepository translationRepository;


    public FlashcardService(FlashcardRepository repository, FlashcardListContentRepository contentRepository, AliasRepository aliasRepository, StatsQuizRepository quizStatsRepository, SingleTranslationRepository translationRepository) {
        this.repository = repository;
        this.contentRepository = contentRepository;
        this.aliasRepository = aliasRepository;
        this.quizStatsRepository = quizStatsRepository;
        this.translationRepository = translationRepository;
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

    public List<SingleTranslation> findPopular(String language, Integer minPopularity) {
        return translationRepository.findFilteredPopularTranslations(language, minPopularity);
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

    //TODO alias is a part of flashcard anyway, make this method private
    public List<String> findAliasByWordId(Long word_id) {
        List<Alias> foundedAliases = aliasRepository.findAliasesByWordId(word_id);
        return foundedAliases.stream().map(Alias::getAlias).toList();
    }

    public FlashcardResponse getFlashcardWithAlias(Flashcard flashcard) {
        List<String> foundedAlias = findAliasByWordId(flashcard.getId());
        return new FlashcardResponse(flashcard, foundedAlias);
    }

    public Page<Flashcard> getFlashcardsUser(Long userId, Pageable pageable, String language) {
        return repository.findFlashcardByIsGlobalFalseAndUserIdAndLanguage(userId, pageable, language);
    }

    public Page<Flashcard> getFlashcardsByKeyword(Pageable pageable, String keyword, String language, String global, Long userId) {
        if(Objects.equals(global, DictionaryChoice.GLOBAL.name().toLowerCase())){
            return repository.searchGlobalFlashcardsWithPagination(pageable, keyword, language);
        } else if(Objects.equals(global, DictionaryChoice.LOCAL.name().toLowerCase())) {
            return repository.searchUsersFlashcardsWithPagination(pageable, keyword, language, userId);
        } else if(Objects.equals(global, DictionaryChoice.BOTH.name().toLowerCase())) {
            return repository.searchFlashcardsWithPagination(pageable, keyword, language, userId);
        }
        return null;
    }

    public boolean deleteFlashcard(Long id){
        long deleted = repository.deleteById(id);
        return deleted > 0;
    }

    public List<FlashcardResponse> getFlashcardsFromList(Long listId){
        ArrayList<FlashcardListContent> content = (ArrayList<FlashcardListContent>) contentRepository.findByListId(listId);
        ArrayList<Flashcard> flashcards = new ArrayList<>();
        //TODO idk why tf map doesnt work here but ill fix it later. it works for now
        for (FlashcardListContent f : content) {
            flashcards.add(findById(f.getFlashcardId()).get());
        }
//        (ArrayList<Flashcard>) content.stream().map(it -> findById(it.getFlashcardId()).get()).toList();
        return flashcards.stream().map(it -> {
            List<String> foundAlias = findAliasByWordId(it.getId());
            return new FlashcardResponse(it, foundAlias);
        }).toList();
    }

    public List<FlashcardResponse> getUnlearnedFlashcardsFromList(Long userId, Long listId){
        List<FlashcardResponse> flashcards = getFlashcardsFromList(listId);
        List<FlashcardResponse> unlearned = flashcards.stream().filter(card -> {
            Optional<FlashcardStatistics> stat = quizStatsRepository.findByUserIdAndFlashcardId(userId, card.getId());
            if(stat.isEmpty()) return true;
            return !stat.get().getLearned();
        }).toList();

        return unlearned;
    }
}
