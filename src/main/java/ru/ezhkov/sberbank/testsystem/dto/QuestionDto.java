package ru.ezhkov.sberbank.testsystem.dto;

import lombok.Builder;
import lombok.Data;
import ru.ezhkov.sberbank.testsystem.enities.Variant;

import java.util.List;

@Data
@Builder
public class QuestionDto {
    Integer id;
    String questionText;
    boolean openQuestion;
    List<Variant> variants;
}
