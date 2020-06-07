package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.enities.Answer;

@Component
public interface AnswerDAO {

    void saveAnswer(Answer answer);
}
