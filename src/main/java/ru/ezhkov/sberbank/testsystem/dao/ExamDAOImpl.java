package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.dto.ExamDto;
import ru.ezhkov.sberbank.testsystem.enities.Exam;

import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ExamDAOImpl implements ExamDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Exam> getUserExams(Integer id) {
        return jdbcTemplate.query(
            "SELECT id, name FROM exams INNER JOIN users_exams ON exams.id = users_exams.exams_id where users_exams.users_id=? ",
            new Object[]{id},
            ((resultSet, row) -> (Exam.builder()
                                      .id(resultSet.getInt("id"))
                                      .examName(resultSet.getString("name")).build())));
    }

    @Override
    public List<Exam> getAllExams() {
        return jdbcTemplate.query("SELECT * FROM exams", ((resultSet, row) -> (Exam.builder()
                                                                                   .id(resultSet.getInt("id"))
                                                                                   .examName(
                                                                                       resultSet.getString("name"))
                                                                                   .build())));
    }

    @Override
    public ExamDto getExamById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM exams WHERE id = ?", new Object[]{id},
                                           ((resultSet, row) -> ExamDto.builder()
                                               .id(resultSet.getInt("id"))
                                               .examName(resultSet.getString("name")).build()));
    }

    @Override
    public Integer createExam(ExamDto exam) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("INSERT INTO exams (name) VALUES(?)", new String[] {"id"});
                ps.setString(1, exam.getExamName());
                return ps;
            }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void addExamForStudents(Integer examId) {
        jdbcTemplate.update("INSERT INTO users_exams (users_id, exams_id) SELECT users.id, ? FROM users WHERE users.role = 'STUDENT'",
                            examId);
    }
}
