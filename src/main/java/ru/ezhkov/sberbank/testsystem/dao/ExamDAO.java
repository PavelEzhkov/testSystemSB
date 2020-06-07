package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.dto.ExamDto;
import ru.ezhkov.sberbank.testsystem.enities.Exam;

import java.util.List;

@Component
public interface ExamDAO {

    List<Exam> getUserExams(Integer id);

    List<Exam> getAllExams();

    ExamDto getExamById(Integer id);

    Integer createExam(ExamDto exam);

    void addExamForStudents(Integer examId);
}
