package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.enities.Variant;

import java.util.List;

@Component
public class VariantDAOImpl implements VariantDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Variant> getVariantsByQuestionId(Integer id) {
        return jdbcTemplate.query("SELECT * FROM variants WHERE question_id = ?", new Object[]{id},
                                  ((resultSet, row) -> new Variant(resultSet.getInt("id"),
                                                                   resultSet.getInt("question_id"),
                                                                   resultSet.getString("answer_text"),false)));
    }

    @Override
    public void createVariantForQuestion(Variant variant, Integer questionId) {
        jdbcTemplate.update("INSERT INTO variants (answer_text, question_id, correct_answer) VALUES (?,?,?)",
                                   variant.getAnswerText(),
                                   questionId, variant.isCorrect());
    }
}
