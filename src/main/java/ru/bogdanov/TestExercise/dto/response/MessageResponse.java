package ru.bogdanov.TestExercise.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для ответа, которое возвращает строку с текстом ошибки и успеха,
 * используется в классе MessageController
 * */
@Data
@AllArgsConstructor
public class MessageResponse {
    String answer;
}
