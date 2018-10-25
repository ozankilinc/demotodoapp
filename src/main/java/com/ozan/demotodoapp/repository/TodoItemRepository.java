package com.ozan.demotodoapp.repository;

import com.ozan.demotodoapp.model.TodoItem;
import com.ozan.demotodoapp.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem,Long> {
    List<TodoItem> findAllByTodoList(TodoList todoList);
}
