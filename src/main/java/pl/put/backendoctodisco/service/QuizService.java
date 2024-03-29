package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.entity.responses.Quiz;
import pl.put.backendoctodisco.repository.AliasRepository;
import pl.put.backendoctodisco.repository.FlashcardListContentRepository;
import pl.put.backendoctodisco.repository.FlashcardRepository;
import pl.put.backendoctodisco.utils.*;
import pl.put.backendoctodisco.utils.quiz.QuizChooseQuestion;
import pl.put.backendoctodisco.utils.quiz.QuizConnectQuestion;
import pl.put.backendoctodisco.utils.quiz.QuizQuestion;
import pl.put.backendoctodisco.utils.quiz.QuizTypeQuestion;

import java.util.*;


@Service
public class QuizService {
    private final FlashcardRepository repository;
    private final FlashcardListContentRepository contentRepository;
    private final AliasRepository aliasRepository;

    public QuizService(FlashcardRepository repository, FlashcardListContentRepository contentRepository, AliasRepository aliasRepository) {
        this.repository = repository;
        this.contentRepository = contentRepository;
        this.aliasRepository = aliasRepository;
    }

    public List<String> getAliasBeside(List<FlashcardResponse> flashcards, FlashcardResponse beside){
        List<String> alias = new ArrayList<>();
        for(FlashcardResponse card : flashcards){
            if(!card.equals(beside)) {
                for (String cardAlias : card.getAlias()) {
                    alias.add(cardAlias);
                }
            }
        }
        Collections.shuffle(alias);
        return alias;
    }

    public Quiz createQuizForCards(List<FlashcardResponse> flashcards, List<FlashcardResponse> learnedFlashcards){
//        Collections.shuffle(flashcards);
        List<QuizQuestion> questions = new ArrayList<>();
        int cardsUsed=0;

        int nrOfQuestions = flashcards.size()/QuizQuestionType.sumCardsNeeded();
        int nrOfCards = QuizQuestionType.usedCards(QuizQuestionType.CONNECT);
        for(int i=0; i<nrOfQuestions*QuizQuestionType.getFrequency(QuizQuestionType.CONNECT); i++){
            questions.add(new QuizConnectQuestion(flashcards.subList(i*nrOfCards, (i+1)*nrOfCards)));
            cardsUsed+=nrOfCards;
        }

        nrOfQuestions = (flashcards.size()-cardsUsed)/(1+(QuizQuestionType.usedCards(QuizQuestionType.CHOOSE)/QuizQuestionType.usedCards(QuizQuestionType.TYPE)));
        for(int i=cardsUsed; i<nrOfQuestions+cardsUsed; i++){
            questions.add(new QuizChooseQuestion(flashcards.get(i), getAliasBeside(learnedFlashcards,flashcards.get(i)).subList(0,3)));
        }
        cardsUsed+=nrOfQuestions;

        for(; cardsUsed<flashcards.size(); cardsUsed++){
            questions.add(new QuizTypeQuestion(flashcards.get(cardsUsed)));
        }

        return new Quiz(questions);
    }


}
