package ru.aston.homework.dao;

import ru.aston.homework.entity.User;

import java.util.List;

public interface UserRepo {

    List<User> index();

    User findById(String id);

    boolean save(User user);

    User update(User user);

    User findByUsername(String username);

    boolean isPresent(String username);
}
