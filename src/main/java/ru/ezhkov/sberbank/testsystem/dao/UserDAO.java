package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.enities.User;

import java.util.List;
import java.util.Optional;

@Component
public interface UserDAO {
    List<User> getAll();

    Optional<User> getById(Integer id);

    Integer createUser(User user);

    Optional<User> getByName(String name);
}
