package ru.ezhkov.sberbank.testsystem.enities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    int id;
    int examId;
    int userId;
    int resultValue;
}
