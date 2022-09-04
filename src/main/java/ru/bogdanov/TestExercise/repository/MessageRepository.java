package ru.bogdanov.TestExercise.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bogdanov.TestExercise.model.Message;

/**
 * Репозиторий для работы с запросами таблицы Message
 * */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Запрос на поиск 10 последних записей
    Page<Message> findAll(Pageable pageable);
}
