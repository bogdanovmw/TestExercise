package ru.bogdanov.TestExercise.repository;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import ru.bogdanov.TestExercise.model.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback
    void findUserByNameTest () {
        User user = User.builder().name("User123").password("password123").build();
        User createdUser = userRepository.save(user);

        User expectedUser = userRepository.findByName(createdUser.getName()).get();
        assertThat(expectedUser).isEqualTo(createdUser);
    }
}