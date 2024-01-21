package ru.aston.homework.dao;

import org.springframework.stereotype.Component;
import ru.aston.homework.dto.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class UserDAO {


    List<User> initdata = Arrays
            .asList(new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e18631042d19"), "name", "pass"),
                    new User(UUID.fromString("efb7ac4e-4b94-42a1-bd00-e1d641042d19"), "na", "pass"),
                    new User(UUID.fromString("ffb7ac4e-4b94-42a1-bd00-e18641042d19"), "da", "pass"),
                    new User(UUID.fromString("bfb7ac4e-4b94-42a1-bd00-e18641042d19"), "ra", "pass"));
    private List<User> users = new ArrayList<>(initdata);

    public List<User> index() {
        return users;
    }


    public User getUserById(String id) {
        User userForm = users.stream().filter(user -> user.getId().equals(UUID.fromString(id))).findAny().orElse(null);
        System.out.println(userForm);
        return userForm;
    }

    public boolean save(User user) {

        users.add(user);
        return true;

    }

    public User update(User user) {
        return users.set(users.indexOf(user), user);

    }

    public User getUserByUsername(String targetUsername) {
        return users.stream()
                .filter(user -> user.getUsername().equals(targetUsername)).findAny().orElse(null);
    }

    public boolean isPresent(String targetUsername) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(targetUsername));
    }
}
