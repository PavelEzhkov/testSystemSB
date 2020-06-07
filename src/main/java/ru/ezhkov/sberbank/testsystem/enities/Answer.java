package ru.ezhkov.sberbank.testsystem.enities;

import lombok.Data;

@Data
public class Answer {
    int id;
    int questionID;
    int userID;
    String answerText;

}
