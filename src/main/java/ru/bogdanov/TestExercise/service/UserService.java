package ru.bogdanov.TestExercise.service;

import ru.bogdanov.TestExercise.model.User;

import java.util.List;

/**
 * Интерфейс с методами, для работы сервисного слоя
 * {@link ru.bogdanov.TestExercise.service.impl.UserServiceImpl} класс реализующий интерфейс
 * */
public interface UserService {
    User findByName(String name);
    List<User> findAll();
}
