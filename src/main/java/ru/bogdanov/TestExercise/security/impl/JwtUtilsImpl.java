package ru.bogdanov.TestExercise.security.impl;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.bogdanov.TestExercise.security.JwtUtils;

import java.util.Date;

/**
 * Класс имплементирующий интерфейс JwtUtils для работы с JWT ключами
 * */
@Component
@Slf4j
public class JwtUtilsImpl implements JwtUtils {
    // Секретное слово для генерации ключа
    @Value("${jwt.app.jwtSecret}")
    private String jwtSecret;

    // Время жизни ключа
    @Value("${jwt.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    /*
        Создание токена по имени пользователя, с указанием времени жизни ключа (jwtExpirationMs)
        и шифровании по секретному слову jwtSecret и алгоритму HS512
    */
    public String generateToken(String name) {
        return Jwts.builder()
                    .setSubject(name)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    // Метод получения из строки jwt ключа, вырезаем первые 7 символов (слово Bearer_) и возвращаем ключ
    public String parseJwt(String headerAuth) {
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer_"))
            return headerAuth.substring(7);
        return null;
    }

    // Проверка существования jwt ключа, в случаи ошибки будут выброшены исключения
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
