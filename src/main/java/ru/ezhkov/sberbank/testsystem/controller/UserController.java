package ru.ezhkov.sberbank.testsystem.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.ezhkov.sberbank.testsystem.enities.Exam;
import ru.ezhkov.sberbank.testsystem.enities.Result;
import ru.ezhkov.sberbank.testsystem.enities.User;
import ru.ezhkov.sberbank.testsystem.exception.TestException;
import ru.ezhkov.sberbank.testsystem.service.ExamService;
import ru.ezhkov.sberbank.testsystem.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class UserController {

    @Autowired
    final UserService userService;

    @Autowired
    final ExamService examService;

    @GetMapping("/users")
    public ResponseEntity<CollectionModel<EntityModel<User>>> getAll() {
        List<EntityModel<User>> users = userService.findAll().stream()
            .map(user -> EntityModel.of(user,
                                        linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel(),
                                        linkTo(methodOn(UserController.class).createUser(user)).withRel("create user")))
                .collect(Collectors.toList());

        return ResponseEntity
            .ok(CollectionModel.of(users, linkTo(methodOn(UserController.class).getAll()).withSelfRel()));
    }

    @GetMapping("/userinfo/{id}")
    public ResponseEntity<EntityModel<User>> getUser(@PathVariable Integer id) {
        return userService.findById(id).map(user -> EntityModel.of(user,
                                                                   linkTo(methodOn(UserController.class).getAll()).withRel("Users"),
                                                                   linkTo(methodOn(UserController.class).createUser(user)).withRel("create user")))
            .map(ResponseEntity::ok).orElseThrow(() -> new TestException("User not found"));
    }



    @GetMapping("/user/{id}/exams")
    public ResponseEntity<CollectionModel<EntityModel<Exam>>> getUserExams(@PathVariable Integer id, Principal principal) {
        if (principal.getName().equals(id.toString() )) {
            List<EntityModel<Exam>>  exams =  examService.getUserExams(id).stream().map(exam -> EntityModel.of(exam)).collect(Collectors.toList());
            return ResponseEntity.ok(CollectionModel.of(exams));
        }
        throw new TestException("you can see only yours results");
    }


    @GetMapping("/user/{id}/result")
    public ResponseEntity<CollectionModel<EntityModel<Result>>> getUserResults(@PathVariable Integer id, Principal principal) {
        if (principal.getName().equals(id.toString())) {
            List<EntityModel<Result>>  results =  examService.getUserResults(id).stream().map(result -> EntityModel.of(result)).collect(Collectors.toList());
            return ResponseEntity.ok(CollectionModel.of(results));
        }
        throw new TestException("you can see only yours results");
    }


    @PostMapping("/users")
    public ResponseEntity<EntityModel<Integer>> createUser(@RequestBody User user) {
        return ResponseEntity.ok(EntityModel.of(userService.crateUser(user),
                                                linkTo(methodOn(UserController.class).getUser(user.getId())).withRel("Show user by id"),
                                                linkTo(methodOn(UserController.class).getAll()).withRel("All users")));
    }
}
