package ru.aston.homework.exeption;

public class EntityAlreadyExistsException extends Exception {
    private String message;

    public EntityAlreadyExistsException(String message) {
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