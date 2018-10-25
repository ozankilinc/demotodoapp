package com.ozan.demotodoapp.model;

import lombok.Data;


import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "todoitems")
public class TodoItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Instant deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    private TodoList todoList;

    public TodoItem() {
    }

    public TodoItem(String name, String description, Status status, Instant deadline) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
    }

    public TodoItem(String name, String description, Status status, Instant deadline, TodoList todoList) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.deadline = deadline;
        this.todoList = todoList;
    }
}
