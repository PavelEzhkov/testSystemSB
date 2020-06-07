package ru.ezhkov.sberbank.testsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ezhkov.sberbank.testsystem.dao.AnswerDAO;
import ru.ezhkov.sberbank.testsystem.dao.ExamDAO;
import ru.ezhkov.sberbank.testsystem.dao.QuestionsDAO;
import ru.ezhkov.sberbank.testsystem.dao.ResultDAO;
import ru.ezhkov.sberbank.testsystem.dao.VariantDAO;
import ru.ezhkov.sberbank.testsystem.dto.ExamDto;
import ru.ezhkov.sberbank.testsystem.dto.QuestionDto;
import ru.ezhkov.sberbank.testsystem.enities.Answer;
import ru.ezhkov.sberbank.testsystem.enities.Exam;
import ru.ezhkov.sberbank.testsystem.enities.Result;
import ru.ezhkov.sberbank.testsystem.enities.Variant;
import ru.ezhkov.sberbank.testsystem.exception.TestException;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ExamServiceImpl implements ExamService {

    @Autowired
    private ExamDAO examDAO;
    @Autowired
    private QuestionsDAO questionsDAO;
    @Autowired
    private VariantDAO variantDAO;
    @Autowired
    private ResultDAO resultDAO;
    @Autowired
    private AnswerDAO answerDAO;

    @Override
    public List<Exam> getUserExams(Integer id) {

        return examDAO.getUserExams(id);
    }

    @Override
    public List<Exam> getAllExams() {
        return examDAO.getAllExams();
    }

    @Override
    public ExamDto getExam(Integer id) {
        ExamDto exam = examDAO.getExamById(id);
        List<QuestionDto> questions = questionsDAO.getQuestionsByExamId(id);
        questions.forEach((questionDto -> {
            if (!questionDto.isOpenQuestion()) {
                questionDto.setVariants(variantDAO.getVariantsByQuestionId(questionDto.getId()));
            }
        }));
        exam.setQuestions(questions);
        return exam;
    }

    @Override
    public List<Result> getUserResults(Integer id) {
        return resultDAO.getResultsByUserId(id);
    }

    @Override
    @Transactional
    public Integer createExam(ExamDto exam) {
        Integer examId = examDAO.createExam(exam);
        exam.getQuestions().forEach(question -> {
            Integer questionId = questionsDAO.createQuestionForExam(question, examId);
            if (question.isOpenQuestion()) {
                addOpenQuestionVariant(question, questionId);
            } else {
                addCloseQuestionVariant(question, questionId);
            }
        });
        examDAO.addExamForStudents(examId);
        return examId;
    }

    @Override
    @Transactional
    public Integer checkAnswers(Integer id, List<Answer> answers, Integer userID) {
        AtomicInteger result = new AtomicInteger();
        answers.forEach(answer -> {
                            answer.setUserID(userID);
                            answerDAO.saveAnswer(answer);
                            if (questionsDAO.getCorrectAnswer(answer.getQuestionID()).equals(answer.getAnswerText())) {
                                result.getAndIncrement();
                            }
                        });
        int resultPros = result.get() * 100 / answers.size(); //todo get number of questions from db
        resultDAO.saveResult(new Result(0, id, userID, resultPros));
        return resultPros;
    }

    private void addCloseQuestionVariant(QuestionDto question, Integer questionId) {
        if (question.getVariants().size() == 4 && isOnlyOneCorrectVariant(question)) {
            question.getVariants().forEach(variant -> variantDAO.createVariantForQuestion(variant, questionId));
        } else {
            throw new TestException(
                "Incorrect number of variants or no correct variant for question: " + question.getQuestionText());
        }
    }

    private void addOpenQuestionVariant(QuestionDto question, Integer questionId) {
        if (question.getVariants().size() == 1 && question.getVariants().get(0).isCorrect()) {
            variantDAO.createVariantForQuestion(question.getVariants().get(0), questionId);
        } else {
            throw new TestException(
                "Incorrect number of variants or no correct variant for question: " + question.getQuestionText());
        }
    }

    private boolean isOnlyOneCorrectVariant(QuestionDto question) {
        return question.getVariants().stream().filter(Variant::isCorrect).count() == 1;
    }


}
