package ru.bogdanov.TestExercise.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import ru.bogdanov.TestExercise.model.Message;
import ru.bogdanov.TestExercise.model.User;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessageRepositoryTest {
    @Autowired
    private MessageRepository messageRepository;

    @Test
    void findAll10LastMessagesTest () {
        PageRequest pr = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("id")));
        List<Message> messages = messageRepository.findAll(pr).getContent();
        assertThat(messages.size()).isGreaterThan(0);
    }

    @Test
    @Rollback
    void saveMessageTest() {
        User user = User.builder().id(1L).name("user1").password("password1").build();
        Message message = Message.builder().user(user).message("Message 1").build();

        Message createdMessage = messageRepository.save(message);
        assertThat(createdMessage.getId()).isPositive();
    }
}