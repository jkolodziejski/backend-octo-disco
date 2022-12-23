package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.DifficultyLevel;
import pl.put.backendoctodisco.entity.TestStatistics;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.entity.responses.Test;
import pl.put.backendoctodisco.entity.test_entity.TestOrderAnswer;
import pl.put.backendoctodisco.repository.DifficultyLevelRepository;
import pl.put.backendoctodisco.repository.StatsTestRepository;
import pl.put.backendoctodisco.repository.test_repository.TestChooseRepository;
import pl.put.backendoctodisco.repository.test_repository.TestOrderAnswerRepository;
import pl.put.backendoctodisco.repository.test_repository.TestOrderQuestionRepository;
import pl.put.backendoctodisco.repository.test_repository.TestTypeRepository;
import pl.put.backendoctodisco.utils.test.TestChooseQuestionResponse;
import pl.put.backendoctodisco.utils.test.TestOrderQuestionResponse;
import pl.put.backendoctodisco.utils.test.TestQuestion;
import pl.put.backendoctodisco.utils.test.TestTypeQuestionResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class LevelService {

    private final DifficultyLevelRepository levelRepository;

    public LevelService(DifficultyLevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public ArrayList<DifficultyLevel> getAllLevels(String language){
        ArrayList<DifficultyLevel> levels = new ArrayList(levelRepository.findByLanguage(language));
        return levels;
    }
}
