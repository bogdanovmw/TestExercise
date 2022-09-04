package ru.bogdanov.TestExercise.service.impl;

import org.springframework.stereotype.Service;
import ru.bogdanov.TestExercise.exeption.UserNotFoundException;
import ru.bogdanov.TestExercise.model.User;
import ru.bogdanov.TestExercise.repository.UserRepository;
import ru.bogdanov.TestExercise.service.UserService;

import java.util.List;

/**
 * Сервисный слой для работы с Users и UserRepository
 * */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
        Метод поиска имени пользователя по имени,
        в случаи если пользователь не найден, будет выброшено исключение UserNotFoundException
    */
    @Override
    public User findByName(String name) {
        return userRepository
                .findByName(name)
                .orElseThrow(() -> new UserNotFoundException(name));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
