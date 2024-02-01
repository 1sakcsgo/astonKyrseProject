package ru.aston.homework.exeption;

public class EntityNotFoundException extends Exception {
    private String message;

    public EntityNotFoundException(String message) {

        this.message = message;
        System.out.println(message);
    }

    @Override
    public String getMessage() {
        return message;
    }

}
