package ru.aston.homework.dao;

import ru.aston.homework.entity.User;

import java.util.List;

public interface UserRepo {

    List<User> index();

    User getUserById(String id);

    boolean save(User user);

    User update(User user);

    User getUserByUsername(String username);

    boolean isPresent(String username);
}
