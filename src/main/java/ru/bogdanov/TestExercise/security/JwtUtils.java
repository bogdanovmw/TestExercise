package ru.bogdanov.TestExercise.security;

/**
 * Интерфейс с методами, для работы c JWT
 * {@link ru.bogdanov.TestExercise.security.impl.JwtUtilsImpl} класс реализующий интерфейс
 * */
public interface JwtUtils {
    String generateToken(String name);
    String parseJwt(String headerAuth);
    boolean validateJwtToken(String authToken);
}
