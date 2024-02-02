package ru.aston.homework.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "users")
public class User {

    /**
     * Поле для хранения уникального индитификатора пользователя
     */
    @Id
    @Column(name ="id")
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
