package ru.aston.homework.dao;

import org.springframework.stereotype.Component;
import ru.aston.homework.entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Класс выступающий репозиторием для пользователя
 */
@Component
public class UserDAOImp implements UserRepo {

    /**
     * Поле необходимо для задания начальных данных
     */
    List<User> initdata = Arrays
            .asList(new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass"),
                    new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e1d641042d19"), "na", "pass"),
                    new User(UUID.fromString("ffb7ac4e-4b94-42a1-bd00-e18641042d19"), "da", "pass"),
                    new User(UUID.fromString("bfb7ac4e-4b94-42a1-bd00-e18641042d19"), "ra", "pass"));
    /**
     * Поле необходимо хранения списка пользователей
     */
    private List<User> users = new ArrayList<>(initdata);// Можно было использовать HashMap для более быстрого доступа по ID

    /**
     * Получает всех пользователей
     * @return список пользователей User
     */
    public List<User> index() {
        return users;
    }

    /**
     * Получает пользователя по id
     * @param id индитификатор пользователя
     * @return обьект пользователя
     */
    public User getUserById(String id) {
        User userForm = users.stream().filter(user -> user.getId().equals(UUID.fromString(id))).findAny().orElse(null);
        System.out.println(userForm);
        return userForm;
    }

    /**
     * Добавляет пользователей в список
     * @param user обьект пользвоателя
     * @return возвращает true при успешном добавлении
     */
    public boolean save(User user) {
        return users.add(user);
    }

    /**
     * Обновляет обьект пользователя в списке
     * @param user обьект пользователя
     * @return возвращает true при успешном добавлении
     */
    public User update(User user) {
        return users.set(users.indexOf(user), user);
    }

    /**
     * Получает пользователя по username
     * @param username имя пользователя
     * @return обьект пользователя
     */
    public User getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username)).findAny().orElse(null);
    }

    /**
     * Проверяет присутствует ли пользователь с таким именем в списке
     * @param username имя пользователя для проверки
     * @return возвращает true, если пользователь присутствует
     */
    public boolean isPresent(String username) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
