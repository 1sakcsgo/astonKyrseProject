package ru.aston.homework.exeption;

public class WrongPasswordException extends Exception {
    private String message;

    public WrongPasswordException(String message) {
        this.message = message;
        System.out.println(message);

    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
