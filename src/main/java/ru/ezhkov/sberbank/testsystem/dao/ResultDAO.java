package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.enities.Result;

import java.util.List;

@Component
public interface ResultDAO {

    List<Result> getResultsByUserId(Integer id);

    void saveResult(Result result);
}
