package ru.bogdanov.TestExercise.service.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.bogdanov.TestExercise.dto.request.MessageRequest;
import ru.bogdanov.TestExercise.model.Message;
import ru.bogdanov.TestExercise.model.User;
import ru.bogdanov.TestExercise.repository.MessageRepository;
import ru.bogdanov.TestExercise.service.UserService;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {
    @Mock
    private UserService userService;
    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    private User user;
    private Message message;
    private MessageRequest messageRequest;

    @Test
    void saveMessageTest () {
        user = userService.findByName("user1");
        message = Message.builder().user(user).message("Message 1").build();
        messageRequest = new MessageRequest("user1", "Message 1");

        when(messageRepository.save(any(Message.class))).thenReturn(message);

        Message expected = messageService.save(messageRequest);
        assertThat(expected).isSameAs(message);

        verify(messageRepository).save(message);
    }


    @Test
    void find10LastMessagesTest() {
        Page<Message> messages = mock(Page.class);
        PageRequest pg = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));

        given(messageRepository.findAll(pg)).willReturn(messages);

        List<Message> expected = messageService.find10LastMessages();
        assertEquals(expected, messages.getContent());

        verify(messageRepository).findAll(pg);
    }
}