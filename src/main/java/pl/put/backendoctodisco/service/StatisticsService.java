package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.FlashcardStatistics;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.requests.QuizResultRequest;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.entity.responses.Quiz;
import pl.put.backendoctodisco.repository.StatsQuizRepository;
import pl.put.backendoctodisco.utils.QuizQuestionType;
import pl.put.backendoctodisco.utils.quiz.QuizChooseQuestion;
import pl.put.backendoctodisco.utils.quiz.QuizConnectQuestion;
import pl.put.backendoctodisco.utils.quiz.QuizQuestion;
import pl.put.backendoctodisco.utils.quiz.QuizTypeQuestion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class StatisticsService {
    private final StatsQuizRepository quizRepository;

    public StatisticsService(StatsQuizRepository quizRepository) {

        this.quizRepository = quizRepository;
    }

    public FlashcardStatistics updateStatistics(QuizResultRequest quizResult, User user){
        for(Long id : quizResult.correct_id){
            quizRepository.update(new FlashcardStatistics(user, id, true));
        }
    }

}
