package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.FlashcardStatistics;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.QuizResultRequest;
import pl.put.backendoctodisco.repository.StatsQuizRepository;

import java.util.Optional;


@Service
public class StatisticsService {
    private final StatsQuizRepository quizRepository;

    public StatisticsService(StatsQuizRepository quizRepository) {
        this.quizRepository = quizRepository;
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

}
