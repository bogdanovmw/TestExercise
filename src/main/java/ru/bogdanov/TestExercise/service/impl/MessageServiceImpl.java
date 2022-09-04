package ru.bogdanov.TestExercise.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.bogdanov.TestExercise.dto.request.MessageRequest;
import ru.bogdanov.TestExercise.model.Message;
import ru.bogdanov.TestExercise.repository.MessageRepository;
import ru.bogdanov.TestExercise.service.MessageService;
import ru.bogdanov.TestExercise.service.UserService;

import java.util.List;

/**
 * Сервисный слой для работы с Message и MessageRepository
 * */
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public MessageServiceImpl(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    // Метод сохранения сообщения в таблицу
    @Override
    public Message save(MessageRequest message) {
        return messageRepository.save(
                Message.builder() // Создаем сообщение
                        .user(userService.findByName(message.getName())) // Ищем пользователя по имени
                        .message(message.getMessage()) // текст сообщения
                        .build()
        );
    }

    // Метод поиска 10 последних записей в таблице Message
    @Override
    public List<Message> find10LastMessages() {
        // Ищем сообщения с 0 по 10 индекса, в порядке убывания
        Page<Message> message = messageRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id"))));
        return message.getContent();
    }

}
