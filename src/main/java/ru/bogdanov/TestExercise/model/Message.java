package ru.bogdanov.TestExercise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

/**
 * Таблица Message с полями (id, id_user, message)
 * */
@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String message;

    /*
        Поле id_user
        Связь между таблицами message и users (М к 1)
     */
    @ManyToOne
    @JoinColumn(name = "id_user")
    @JsonIgnore
    private User user;
}
