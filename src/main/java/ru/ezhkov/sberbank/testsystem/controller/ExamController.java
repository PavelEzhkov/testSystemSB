package ru.ezhkov.sberbank.testsystem.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ezhkov.sberbank.testsystem.dto.ExamDto;
import ru.ezhkov.sberbank.testsystem.enities.Answer;
import ru.ezhkov.sberbank.testsystem.enities.Exam;
import ru.ezhkov.sberbank.testsystem.enities.User;
import ru.ezhkov.sberbank.testsystem.service.ExamService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class ExamController {

    @Autowired
    final ExamService examService;

    @GetMapping("/exams")
    public ResponseEntity<CollectionModel<EntityModel<Exam>>> getAll() {
        List<EntityModel<Exam>> exams = examService.getAllExams().stream()
            .map(EntityModel::of).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(exams));
    }

    @PostMapping("/exams")
    public ResponseEntity<EntityModel<Integer>> createExam(@RequestBody ExamDto exam) {
        Integer examID = examService.createExam(exam);
        return ResponseEntity.ok(EntityModel.of(examID));
    }

    @GetMapping("/exams/{id}")
    public ResponseEntity<EntityModel<ExamDto>> getExam(@PathVariable Integer id) {
        ExamDto exam = examService.getExam(id);
        return ResponseEntity.ok(EntityModel.of(exam));
    }

    @PostMapping("/exam/{id}")
    public ResponseEntity<EntityModel<Integer>> sendExamAnswers(@PathVariable Integer id, @RequestBody List<Answer> answers, Principal principal) {
        return ResponseEntity.ok(EntityModel.of(examService.checkAnswers(id, answers, Integer.valueOf(principal.getName()))));
    }

}
