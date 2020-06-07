package ru.ezhkov.sberbank.testsystem.enities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    Integer id;
    String name;
    String password;
    UserRole role;
}
