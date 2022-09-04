package ru.bogdanov.TestExercise.security.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.bogdanov.TestExercise.security.JwtUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtUtilsImplTest {
    @Autowired
    private JwtUtils jwtUtils;

    @Test
    void generateTokenTest() {
        String token = jwtUtils.generateToken("user1");
        assertNotNull(token);
    }


    @Test
    void parseJwtTest() {
        String token = "eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY2MjIyODk0MSwiZXhwIjoxNjYyMjI5MDAxfQ";
        String tokenBearer = "Bearer_eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY2MjIyODk0MSwiZXhwIjoxNjYyMjI5MDAxfQ";

        String expected = jwtUtils.parseJwt(tokenBearer);
        assertEquals(expected, token);
    }

    @Test
    void validateJwtTokenOnExistsTest() {
        String token = jwtUtils.generateToken("user1");
        boolean validateToken = jwtUtils.validateJwtToken(token);

        assertTrue(validateToken);
    }

}