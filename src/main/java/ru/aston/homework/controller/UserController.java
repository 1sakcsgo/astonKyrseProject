package ru.aston.homework.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.homework.Exeption.EntityAlreadyExistsException;
import ru.aston.homework.Exeption.EntityNotFoundException;
import ru.aston.homework.Exeption.WrongPasswordException;
import ru.aston.homework.dto.UserForm;
import ru.aston.homework.entity.User;
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
    public User userById(@PathVariable("id") String id) throws EntityNotFoundException {

        return userService.getUserById(id);

    }

    @PostMapping(value = "/signUp")
    @ResponseBody
    public ResponseEntity<User> signUp(@RequestBody User user) throws EntityAlreadyExistsException {

        return userService.signUp(user);

    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<User> loginUser(@RequestBody User user) throws WrongPasswordException {

        return userService.login(user);

    }

    @PostMapping(value = "/changePass/{id}")
    @ResponseBody
    public ResponseEntity<User> changePass(@RequestBody UserForm userForm,@PathVariable("id") String id) throws  EntityAlreadyExistsException, EntityNotFoundException {

        return userService.changePass(userForm,id);

    }

    @GetMapping("allUser")
    public List<User> showAllUser() {
        return userService.getAllUser();
    }
}
