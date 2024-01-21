package ru.aston.homework.Exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExeptionHandler {
    @ExceptionHandler(UserExeption.class)
    ResponseEntity handle(UserExeption ex) {
        return new ResponseEntity<>(ex.message, HttpStatus.CONFLICT);
    }

}
