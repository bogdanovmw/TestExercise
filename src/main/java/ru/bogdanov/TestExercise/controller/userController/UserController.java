package ru.bogdanov.TestExercise.controller.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bogdanov.TestExercise.dto.request.UserRequest;
import ru.bogdanov.TestExercise.dto.response.JwtTokenResponse;
import ru.bogdanov.TestExercise.model.User;
import ru.bogdanov.TestExercise.security.JwtUtils;
import ru.bogdanov.TestExercise.service.UserService;

import java.util.List;

/**
 * Контроллер для работы запроса авторизации пользователя
 * */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Post запрос получающий на вход UserRequest и возвращающий в случаи успеха jwt токен
    @PostMapping
    public ResponseEntity<?> authUser(@RequestBody UserRequest userRequest) {
        User userDB = userService.findByName(userRequest.getName()); // Поиск пользователя в БД

        if (!userDB.getPassword().equals(userRequest.getPassword())) // Проверка пароля пришедшего в теле запроса и пароля хранившегося в БД
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Не верный пароль!"); // В случаи ошибки возвращаем ответ

        // Генерируем токен по имени пользователя
        String token = jwtUtils.generateToken(userDB.getName());

        return ResponseEntity.status(HttpStatus.OK).body(new JwtTokenResponse(token)); // Возвращаем ответ создавшийся токен
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }
}
