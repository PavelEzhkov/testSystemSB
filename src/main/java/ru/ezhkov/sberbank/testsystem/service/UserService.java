package ru.ezhkov.sberbank.testsystem.service;

import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.enities.User;

import java.util.List;
import java.util.Optional;

@Component
public interface UserService {

    List<User> findAll();

    Optional<User> findById(Integer id);

    Optional<User> findByName(String name);

    Integer crateUser(User user);
}
