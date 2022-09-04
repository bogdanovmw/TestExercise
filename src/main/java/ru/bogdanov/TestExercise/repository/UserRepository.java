package ru.bogdanov.TestExercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bogdanov.TestExercise.model.User;

import java.util.Optional;

/**
 * Репозиторий для работы с запросами таблицы Users
 * */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Запрос на поиск записи по имени
    Optional<User> findByName(String name);
}
