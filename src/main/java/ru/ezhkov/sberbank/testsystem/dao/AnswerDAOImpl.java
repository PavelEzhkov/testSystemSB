package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.enities.Answer;

@Component
public class AnswerDAOImpl implements AnswerDAO {

    @Autowired
    private
    JdbcTemplate jdbcTemplate;

    @Override
    public void saveAnswer(Answer answer) {
        jdbcTemplate.update("INSERT INTO answers (question_id, users_id, answer_text) VALUES (?,?,?)",
                            answer.getQuestionID(), answer.getUserID(), answer.getAnswerText());
    }
}
