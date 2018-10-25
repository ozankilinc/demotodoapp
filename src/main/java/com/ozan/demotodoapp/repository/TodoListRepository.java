package com.ozan.demotodoapp.repository;

import com.ozan.demotodoapp.model.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList,Long> {
}
