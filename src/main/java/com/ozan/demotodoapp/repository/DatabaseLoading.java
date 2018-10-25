package com.ozan.demotodoapp.repository;

import com.ozan.demotodoapp.model.Status;
import com.ozan.demotodoapp.model.TodoItem;
import com.ozan.demotodoapp.model.TodoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.util.List;

@Component
@ConditionalOnProperty(name = "todoItem.db.recreate",havingValue = "true")
public class DatabaseLoading implements ApplicationListener<ContextRefreshedEvent> {
    private TodoItemRepository todoItemRepository;
    private TodoListRepository todoListRepository;


    @Autowired
    public DatabaseLoading(TodoItemRepository todoItemRepository, TodoListRepository todoListRepository) {
        this.todoItemRepository = todoItemRepository;
        this.todoListRepository = todoListRepository;
    }


    public void loadData() {
        this.todoItemRepository.deleteAll();
        this.todoListRepository.deleteAll();

        //Save deafult TodoList
        TodoList todoList1 = new TodoList("Evde yapılacaklar");



        TodoList todoList2 = new TodoList("İsde yapılacakar");


        //Save the TodoItem
        TodoItem todoItem1 = new TodoItem("is","is de şunu yapmalısın",Status.Completed,Instant.parse("2018-12-12T18:00:00.000Z"));
        TodoItem todoItem2 = new TodoItem("okul","kitap okunacak",Status.NotCompleted,Instant.parse("2018-12-12T18:00:00.000Z"));
        TodoItem todoItem3 = new TodoItem("cinema","film izlenecek",Status.NotCompleted,Instant.parse("2018-12-12T18:00:00.000Z"));
        todoList1.addTodoItem(todoItem1);
        todoList1.addTodoItem(todoItem2);
        todoList2.addTodoItem(todoItem3);

        todoItemRepository.save(todoItem1);
        todoItemRepository.save(todoItem2);
        todoItemRepository.save(todoItem3);

        todoListRepository.save(todoList1);
        todoListRepository.save(todoList2);



        System.out.println("Database initialized");


    }

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadData();

    }
}
