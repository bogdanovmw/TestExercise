package ru.bogdanov.TestExercise.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO для ответа на запрос, которое возвращает ключ jwt
 * */
@Data
@AllArgsConstructor
public class JwtTokenResponse {
    private String token;
}
