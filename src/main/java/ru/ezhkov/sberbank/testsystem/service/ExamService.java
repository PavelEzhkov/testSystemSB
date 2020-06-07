package ru.ezhkov.sberbank.testsystem.service;

import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.dto.ExamDto;
import ru.ezhkov.sberbank.testsystem.enities.Answer;
import ru.ezhkov.sberbank.testsystem.enities.Exam;
import ru.ezhkov.sberbank.testsystem.enities.Result;

import java.util.List;

@Component
public interface ExamService {

    List<Exam> getUserExams(Integer id);

    List<Exam> getAllExams();

    ExamDto getExam(Integer id);

    List<Result> getUserResults(Integer id);

    Integer createExam(ExamDto exam);

    Integer checkAnswers(Integer id, List<Answer> answers, Integer userID);
}
