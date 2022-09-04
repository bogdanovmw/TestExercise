package ru.bogdanov.TestExercise.service;

import ru.bogdanov.TestExercise.dto.request.MessageRequest;
import ru.bogdanov.TestExercise.model.Message;

import java.util.List;

/**
 * Интерфейс с методами, для работы сервисного слоя
 * {@link ru.bogdanov.TestExercise.service.impl.MessageServiceImpl} класс реализующий интерфейс
 * */
public interface MessageService {
    Message save(MessageRequest message);
    List<Message> find10LastMessages();
}
