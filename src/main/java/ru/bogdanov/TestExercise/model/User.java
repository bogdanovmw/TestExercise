package ru.bogdanov.TestExercise.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Таблица Users с полями (id, name, password)
 * */
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String password;

    // Связь между таблицами users и message (1 к М)
    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Message> messages;
}
