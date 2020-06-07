package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.enities.Result;

import java.util.List;

@Component
public class ResultDAOImpl implements ResultDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Result> getResultsByUserId(Integer id) {
        return  jdbcTemplate.query("SELECT * FROM results WHERE user_id = ?", new Object[]{id},
                                   ((resultSet, row) -> new Result(resultSet.getInt("id"),
                                                                   resultSet.getInt("exams_id"),
                                                                   resultSet.getInt("users_id"),
                                                                   resultSet.getInt("result"))));
    }

    @Override
    public void saveResult(Result result) {
        jdbcTemplate.update("INSERT INTO results (exams_id, users_id, result) VALUES (?,?,?)",
                            result.getExamId(), result.getUserId(), result.getResultValue());
    }
}
