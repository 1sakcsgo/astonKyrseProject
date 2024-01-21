package ru.aston.homework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Класс сущности пользователь
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    /**
     * Поле для хранения уникального индитификатора пользователя
     */
    private UUID id;

    /**
     * Поле для хранения имени пользователя
     */
    private String username;

    /**
     * Поле для хранения пароля пользователя
     */
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
