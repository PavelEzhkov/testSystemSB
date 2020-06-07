package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.enities.User;
import ru.ezhkov.sberbank.testsystem.enities.UserRole;

import java.util.List;
import java.util.Optional;

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users",
                                  ((resultSet, row) -> new User(
                                      resultSet.getInt("id"),
                                      resultSet.getString("name"),
                                      resultSet.getString("password"),
                                      UserRole.valueOf(resultSet.getString("role")))));
    }

    @Override
    public Optional<User> getById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id = ?", new Object[]{id},
                                           ((resultSet, row) -> Optional.of(new User(
                                               resultSet.getInt("id"),
                                               resultSet.getString("name"),
                                               resultSet.getString("password"),
                                               UserRole.valueOf(resultSet.getString("role"))))));
    }

    @Override
    public Integer createUser(User user) {
        return jdbcTemplate.update("INSERT INTO users (name, password, role) VALUES (?,?,?)",
                                  user.getName(),
                                  user.getPassword(),
                                  user.getRole().toString());
    }

    @Override
    public Optional<User> getByName(String name) {
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE name = ?", new Object[]{name},
                                           ((resultSet, row) -> Optional.of(new User(
                                               resultSet.getInt("id"),
                                               resultSet.getString("name"),
                                               resultSet.getString("password"),
                                               UserRole.valueOf(resultSet.getString("role"))))));
    }
}
