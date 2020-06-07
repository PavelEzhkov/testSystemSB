package ru.ezhkov.sberbank.testsystem.dto;

import lombok.Builder;
import lombok.Data;
import ru.ezhkov.sberbank.testsystem.enities.Answer;
import ru.ezhkov.sberbank.testsystem.enities.Question;

import java.util.List;

@Data
@Builder
public class ExamDto {
    int id;
    String examName;
    List<QuestionDto> questions;
}
