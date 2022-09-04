package ru.bogdanov.TestExercise.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ru.bogdanov.TestExercise.controller.userController.UserController;
import ru.bogdanov.TestExercise.dto.request.UserRequest;
import ru.bogdanov.TestExercise.exeption.UserNotFoundException;
import ru.bogdanov.TestExercise.model.User;
import ru.bogdanov.TestExercise.security.impl.JwtUtilsImpl;
import ru.bogdanov.TestExercise.service.impl.UserServiceImpl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtilsImpl jwtUtils;
    @MockBean
    private UserServiceImpl userService;

    private User user = User.builder().id(1L).name("user1").password("password1").build();
    private UserRequest userRequest = UserRequest.builder().name("user1").password("password1").build();

    private MockHttpServletRequestBuilder getMockHttpServletRequestBuilder(UserRequest request) throws JsonProcessingException {
        return post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request));
    }

    @Test
    void authUserIfUserFoundAndPasswordTrue() throws Exception {
        given(userService.findByName("user1")).willReturn(user);
        String token = jwtUtils.generateToken(user.getName());

        mockMvc.perform(getMockHttpServletRequestBuilder(userRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(token)));
    }

    @Test
    void authUserIfUserNotFound () throws Exception {
        doThrow(new UserNotFoundException(user.getName())).when(userService).findByName(user.getName());

        mockMvc.perform(getMockHttpServletRequestBuilder(userRequest))
                .andExpect(status().isNotFound());
    }

    @Test
    void checkPasswordUserIfResultFalse () throws Exception {
        UserRequest userRequestBadPassword = UserRequest.builder().name("user1").password("password2").build();
        given(userService.findByName("user1")).willReturn(user);

        assertNotEquals(user.getPassword(), userRequestBadPassword.getPassword());

        mockMvc.perform(getMockHttpServletRequestBuilder(userRequestBadPassword))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Не верный пароль!"));

    }
}