package ru.ezhkov.sberbank.testsystem.enities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Variant {
    Integer id;
    Integer questionId;
    String answerText;
    boolean correct;
}
