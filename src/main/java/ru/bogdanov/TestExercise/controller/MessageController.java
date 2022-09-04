package ru.bogdanov.TestExercise.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bogdanov.TestExercise.dto.request.MessageRequest;
import ru.bogdanov.TestExercise.dto.response.MessageResponse;
import ru.bogdanov.TestExercise.security.JwtUtils;
import ru.bogdanov.TestExercise.service.MessageService;
import ru.bogdanov.TestExercise.service.UserService;

/**
 * Контроллер для работы запроса сохранения сообщения или получения данных
 * */
@RestController
@RequestMapping("/api/message")
public class MessageController {
    private final JwtUtils jwtUtils;
    private final MessageService messageService;
    private final UserService userService;

    public MessageController(JwtUtils jwtUtils, MessageService messageService, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.messageService = messageService;
        this.userService = userService;
    }

    // Post запрос получающий на вход MessageRequest(name, message) и заголовок AUTHORIZATION для работы с jwt ключом
    @PostMapping
    public ResponseEntity<?> message(@RequestBody MessageRequest messageRequest, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        String jwt = jwtUtils.parseJwt(token); // Извлекаем токен из заголовка

        if (token != null && jwtUtils.validateJwtToken(jwt)) { // Проверка существования токена
            /*
                Если пришло сообщение вида { name: "имя отправителя", message: "history 10" }
            */
            if (messageRequest.getMessage().equals("history 10")) { // Проверяем если поле message равно history 10
                return ResponseEntity.ok().body(messageService.find10LastMessages()); // Находим в БД 10 последних записей и возвращаем в ответ список
            } else {
                /*
                    Если пришло сообщение вида { name: "имя отправителя", message: "текст сообщение" }
                */
                messageService.save(messageRequest); // Сохраняем сообщение в таблицу
                return ResponseEntity.ok().body(new MessageResponse("Успешно сохранено.")); // Возвращаем в ответ сообщение
            }
        } else { // Если токена не существует, вернется следующий результат
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageResponse("Ошибка токена."));
        }
    }
}
