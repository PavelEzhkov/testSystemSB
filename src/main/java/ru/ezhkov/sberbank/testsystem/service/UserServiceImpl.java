package ru.ezhkov.sberbank.testsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ezhkov.sberbank.testsystem.dao.UserDAO;
import ru.ezhkov.sberbank.testsystem.enities.User;
import ru.ezhkov.sberbank.testsystem.exception.TestException;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> findAll() {
        return userDAO.getAll();
    }

    @Override
    public Optional<User> findById(Integer id) {
        return userDAO.getById(id);
    }

    @Override
    public Optional<User> findByName(String name) {
        return userDAO.getByName(name);
    }

    @Override
    public Integer crateUser(User user) {
        long  count = userDAO.getAll().stream().map(User::getName).filter(name -> name.equalsIgnoreCase(user.getName())).count();
        if (count == 0) {
            return userDAO.createUser(user);
        } else {
            throw new TestException("User with name "+ user.getName() +" already exist");
        }
    }
}
