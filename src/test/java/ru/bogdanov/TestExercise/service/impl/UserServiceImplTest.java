package ru.bogdanov.TestExercise.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.bogdanov.TestExercise.exeption.UserNotFoundException;
import ru.bogdanov.TestExercise.model.User;
import ru.bogdanov.TestExercise.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user = User.builder().id(1L).name("user1").password("password1").build();

    @Test
    void findUserByNameTest() {
        when(userRepository.findByName(user.getName())).thenReturn(Optional.of(user));

        User expected = userService.findByName(user.getName());
        assertThat(expected).isSameAs(user);

        verify(userRepository).findByName(user.getName());
    }

    @Test
    void findUserByNameIfNotFound() {
        given(userRepository.findByName(user.getName())).willReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                        UserNotFoundException.class,
                        () -> userService.findByName(this.user.getName()));

        assertTrue(exception.getMessage().contains("user1 не найден!"));
    }
}