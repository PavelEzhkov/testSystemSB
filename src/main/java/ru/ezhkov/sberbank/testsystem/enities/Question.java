package ru.ezhkov.sberbank.testsystem.enities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Question {
    Integer id;
    Integer examId;
    String questionText;
    boolean openQuestion;
}
