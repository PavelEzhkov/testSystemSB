package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.dto.QuestionDto;
import ru.ezhkov.sberbank.testsystem.enities.Answer;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class QuestionsDAOImpl implements QuestionsDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<QuestionDto> getQuestionsByExamId(Integer id) {
        return jdbcTemplate.query("SELECT * FROM questions WHERE exams_id = ?", new Object[]{id},
                                  ((resultSet, row) ->  QuestionDto.builder().id(resultSet.getInt("id"))
                                      .questionText(resultSet.getString("question_text"))
                                      .openQuestion(resultSet.getBoolean("open_question"))
                                      .build()));
    }

    @Override
    public Integer createQuestionForExam(QuestionDto question, Integer examId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement
                ps = connection.prepareStatement("INSERT INTO questions (exams_id, question_text,  open_question)  VALUES(?,?,?)", new String[] {"id"});
            ps.setInt(1, examId);
            ps.setString(2, question.getQuestionText());
            ps.setInt(3, question.isOpenQuestion()? 1:0);
            return ps;
        }, keyHolder);
        return (Integer) keyHolder.getKey();
    }

    @Override
    public String getCorrectAnswer(Integer id) {
        return jdbcTemplate.queryForObject("SELECT answer_text FROM variants WHERE question_id = ? AND correct_answer = '1'", new Object[]{id}, String.class);
    }
}
