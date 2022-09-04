package ru.bogdanov.TestExercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.bogdanov.TestExercise.dto.request.MessageRequest;
import ru.bogdanov.TestExercise.model.Message;
import ru.bogdanov.TestExercise.model.User;
import ru.bogdanov.TestExercise.security.JwtUtils;
import ru.bogdanov.TestExercise.service.impl.MessageServiceImpl;
import ru.bogdanov.TestExercise.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private MessageServiceImpl messageService;
    @MockBean
    private UserServiceImpl userService;

    private String token = "eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY2MjIyODk0MSwiZXhwIjoxNjYyMjI5MDAxfQ";
    private String tokenBearer = "Bearer_eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY2MjIyODk0MSwiZXhwIjoxNjYyMjI5MDAxfQ";

    @Test
    void checkJWTTokenIfValidFalseTest () throws Exception {
        MessageRequest messageRequest = MessageRequest.builder().name("user1").message("message1").build();

        given(jwtUtils.parseJwt(tokenBearer)).willReturn(token);
        given(jwtUtils.validateJwtToken(token)).willReturn(false);

        mockMvc.perform(getMockHttpServletRequestBuilder(messageRequest))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.answer", is("Ошибка токена.")));
    }


    @Test
    void getMessageForSaveTest () throws Exception {
        Message message = Message.builder().user(userService.findByName("user1")).message("Message 1").build();
        MessageRequest messageRequest = MessageRequest.builder().name("user1").message("message1").build();

        given(jwtUtils.parseJwt(tokenBearer)).willReturn(token);
        given(jwtUtils.validateJwtToken(token)).willReturn(true);
        given(messageService.save(messageRequest)).willReturn(message);

        assertNotEquals("history 10", messageRequest.getMessage());

        mockMvc.perform(getMockHttpServletRequestBuilder(messageRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.answer", is("Успешно сохранено.")));
    }


    @Test
    void get10LastMessageTest () throws Exception {
        List<Message> messages = new ArrayList<>();
        User user = User.builder().id(1L).name("user1").password("password1").build();
        Message message = Message.builder().user(user).message("Message 1").build();
        messages.add(message);

        MessageRequest messageRequest = MessageRequest.builder().name("user1").message("history 10").build();

        given(jwtUtils.parseJwt(tokenBearer)).willReturn(token);
        given(jwtUtils.validateJwtToken(token)).willReturn(true);
        given(messageService.find10LastMessages()).willReturn(messages);

        assertEquals("history 10", messageRequest.getMessage());

        mockMvc.perform(getMockHttpServletRequestBuilder(messageRequest))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(message.getId())));
    }

    private MockHttpServletRequestBuilder getMockHttpServletRequestBuilder(MessageRequest messageRequest) throws JsonProcessingException {
        MockHttpServletRequestBuilder mhrb = post("/api/message")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", tokenBearer)
                .content(objectMapper.writeValueAsString(messageRequest));
        return mhrb;
    }
}