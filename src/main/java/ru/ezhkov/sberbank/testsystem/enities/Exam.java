package ru.ezhkov.sberbank.testsystem.enities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Exam {
    int id;
    String examName;
    List<Integer> questionIDs;
    List<Integer> studentIDs;
}
