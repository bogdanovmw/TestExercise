package ru.bogdanov.TestExercise.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для получения и работы данных(name, message) из post запроса
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageRequest {
    private String name;
    private String message;
}
