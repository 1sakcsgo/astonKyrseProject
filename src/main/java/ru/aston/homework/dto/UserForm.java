package ru.aston.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Обьект(dto) пользователя для изменения данных
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForm {
    /**
     * Поле с именем пользователя
     */
    private String username;
    /**
     * Поле с текущим паролем пользователя
     */
    private String password;
    /**
     * Поле с новым паролем пользователя
     */
    private String newPass;


}
