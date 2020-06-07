package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.dto.QuestionDto;
import ru.ezhkov.sberbank.testsystem.enities.Answer;

import java.util.List;

@Component
public interface QuestionsDAO {

    List<QuestionDto> getQuestionsByExamId(Integer id);

    Integer createQuestionForExam(QuestionDto question, Integer examId);

    String getCorrectAnswer(Integer id);
}
