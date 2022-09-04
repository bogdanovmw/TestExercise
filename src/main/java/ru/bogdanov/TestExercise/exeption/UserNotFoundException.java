package ru.bogdanov.TestExercise.exeption;

/**
 * Исключение возникающее при отсутствии пользователя
 * */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String name) {
        super("Пользователь: " + name + " не найден!");
    }
}
