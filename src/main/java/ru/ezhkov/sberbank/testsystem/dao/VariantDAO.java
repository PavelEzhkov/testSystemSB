package ru.ezhkov.sberbank.testsystem.dao;

import org.springframework.stereotype.Component;
import ru.ezhkov.sberbank.testsystem.enities.Variant;

import java.util.List;

@Component
public interface VariantDAO {

    List<Variant> getVariantsByQuestionId(Integer id);

    void createVariantForQuestion(Variant variant, Integer questionId);
}
