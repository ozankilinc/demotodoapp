package com.ozan.demotodoapp.util;

import com.ozan.demotodoapp.model.Status;
import com.ozan.demotodoapp.model.TodoItem;
import com.ozan.demotodoapp.model.TodoList;
import com.ozan.demotodoapp.repository.TodoItemRepository;
import com.ozan.demotodoapp.repository.TodoListRepository;
import com.ozan.demotodoapp.viewModel.TodoItemViewModel;
import com.ozan.demotodoapp.viewModel.TodoListViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    private TodoListRepository todoListRepository;
    private TodoItemRepository todoItemRepository;

    @Autowired
    public Mapper(TodoListRepository todoListRepository,TodoItemRepository todoItemRepository) {
        this.todoListRepository = todoListRepository;
        this.todoItemRepository=todoItemRepository;
    }

    public TodoItemViewModel convertToTodoItemViewModel(TodoItem todoItem){
        TodoItemViewModel todoItemViewModel = new TodoItemViewModel();
        todoItemViewModel.setId(todoItem.getId());
        todoItemViewModel.setName(todoItem.getName());
        todoItemViewModel.setDescription(todoItem.getDescription());
        todoItemViewModel.setDeadline(todoItem.getDeadline());
        todoItemViewModel.setStatus(String.valueOf(todoItem.getStatus()));
        todoItemViewModel.setTodoListId(todoItem.getTodoList().getId());
        return todoItemViewModel;

    }

    public TodoItem convertToTodoItem(TodoItemViewModel todoItemViewModel){
        if(todoItemViewModel.getId()!=null){
            TodoItem todoItem = this.todoItemRepository.findById(todoItemViewModel.getId()).get();
            TodoItem newTodoItem =  new TodoItem(todoItemViewModel.getName(),todoItemViewModel.getDescription(),
                    Status.valueOf(todoItemViewModel.getStatus()),todoItemViewModel.getDeadline());
            newTodoItem.setId(todoItem.getId());
            return newTodoItem;

        }
        TodoItem todoItem =
                new TodoItem(todoItemViewModel.getName(),todoItemViewModel.getDescription(),
                        Status.valueOf(todoItemViewModel.getStatus()),todoItemViewModel.getDeadline());
        return todoItem;
    }

    public TodoListViewModel convertToTodoListViewModel(TodoList todoList){
        TodoListViewModel todoListViewModel = new TodoListViewModel();
        todoListViewModel.setId(todoList.getId());
        todoListViewModel.setName(todoList.getName());
        todoListViewModel.setTlTodoItems(todoList.getItems().size());
        return todoListViewModel;
    }

    public TodoList convertToTodoList(TodoListViewModel todoListViewModel){
        TodoList todoList = new TodoList(todoListViewModel.getName());
        return  todoList;
    }


}
