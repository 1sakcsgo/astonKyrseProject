package ru.aston.homework.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<User> userById(@PathVariable("id") String id) throws EntityNotFoundException {

        return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);

    }

    @PostMapping(value = "/signUp")
    @ResponseBody
    public ResponseEntity<User> signUp(@RequestBody User user) throws EntityAlreadyExistsException {
        return new ResponseEntity<>(userService.signUp(user), HttpStatus.OK);


    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ResponseEntity<User> loginUser(@RequestBody User user) throws WrongPasswordException {

        return new ResponseEntity<>(userService.login(user), HttpStatus.OK) ;

    }

    @PostMapping(value = "/changePass/{id}")
    @ResponseBody
    public ResponseEntity<User> changePass(@RequestBody UserForm userForm,@PathVariable("id") String id) throws  EntityAlreadyExistsException, EntityNotFoundException {
        return new ResponseEntity<>(userService.changePass(userForm,id), HttpStatus.OK);


    }

    @GetMapping("allUser")
    public ResponseEntity <List<User>> showAllUser() throws EntityNotFoundException {
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }
}
