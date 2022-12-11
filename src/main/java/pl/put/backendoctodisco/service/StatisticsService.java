package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.FlashcardListContent;
import pl.put.backendoctodisco.entity.FlashcardStatistics;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.QuizResultRequest;
import pl.put.backendoctodisco.entity.responses.CardListStatistics;
import pl.put.backendoctodisco.repository.FlashcardListContentRepository;
import pl.put.backendoctodisco.repository.StatsQuizRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class StatisticsService {

    private final StatsQuizRepository quizRepository;
    private final FlashcardListContentRepository listRepository;

    public StatisticsService(StatsQuizRepository quizRepository, FlashcardListContentRepository listRepository) {
        this.quizRepository = quizRepository;
        this.listRepository = listRepository;
    }

    public void updateStatistics(QuizResultRequest quizResult, User user){
        if(quizResult.correct_id != null) {
            quizResult.correct_id.forEach( id -> {
                if (quizRepository.updateCard(user.getId(), id, true)<1) {
                    quizRepository.save(new FlashcardStatistics(user, id, true));
                }
            });
        }
        if(quizResult.incorrect_id != null) {
            quizResult.incorrect_id.forEach( id -> {
                if (quizRepository.findByUserIdAndFlashcardId(user.getId(), id).isEmpty()) {
                    quizRepository.save(new FlashcardStatistics(user, id, false));
                }
            });
        }
    }

    public CardListStatistics findFlashcardListStatistics(User user, Long list_id){
        List<FlashcardListContent> listContent = listRepository.findByListId(list_id);
        List<Long> learned= new ArrayList<>();
        List<Long> notLearned= new ArrayList<>();
        List<Long> notAttempted= new ArrayList<>();
        listContent.forEach(card -> {
            Optional<FlashcardStatistics> stat = quizRepository.findByUserIdAndFlashcardId(user.getId(), card.getFlashcardId());
            if(stat.isEmpty()){
                notAttempted.add(card.getFlashcardId());
            }else{
                if(stat.get().getLearned()){
                    learned.add(card.getFlashcardId());
                }else{
                    notLearned.add(card.getFlashcardId());
                }
            }
        });
        return new CardListStatistics(learned.size(), notLearned.size(), notAttempted.size());
    }
}
