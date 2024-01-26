package ru.aston.homework.Exeption;

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
