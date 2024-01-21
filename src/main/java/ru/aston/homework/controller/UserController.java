package ru.aston.homework.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.homework.Exeption.UserExeption;
import ru.aston.homework.dto.User;
import ru.aston.homework.dto.UserForm;
import ru.aston.homework.service.UserService;


import java.util.List;

@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "user/{id}")
    @ResponseBody
    public User userById(@PathVariable("id") String id) {

        return userService.getUserById(id);

    }

    @PostMapping(value = "/addUser")
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody User user) throws UserExeption {

        return userService.saveUser(user);

    }

    @PostMapping(value = "/loginUser")
    @ResponseBody
    public ResponseEntity<User> loginUser(@RequestBody User user) throws UserExeption {

        return userService.login(user);

    }

    @PostMapping(value = "/changePass")
    @ResponseBody
    public ResponseEntity<User> changePass(@RequestBody UserForm user) throws UserExeption {

        return userService.changePass(user);

    }

    @GetMapping("allUser")
    public List<User> showAllUser() {
        return userService.getAllUser();
    }
}
