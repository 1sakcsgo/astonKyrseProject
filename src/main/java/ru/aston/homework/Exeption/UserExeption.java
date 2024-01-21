package ru.aston.homework.Exeption;

public class UserExeption extends Exception {
    String message;

    public UserExeption(String message) {
        this.message = message;
        System.out.println(message);
    }
}