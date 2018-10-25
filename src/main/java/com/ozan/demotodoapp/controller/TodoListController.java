package com.ozan.demotodoapp.controller;

import com.ozan.demotodoapp.model.TodoList;
import com.ozan.demotodoapp.repository.TodoListRepository;
import com.ozan.demotodoapp.util.Mapper;
import com.ozan.demotodoapp.viewModel.TodoListViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.ValidationException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todolists")
public class TodoListController {

    private TodoListRepository todoListRepository;
    private Mapper mapper;

    @Autowired
    public TodoListController(TodoListRepository todoListRepository, Mapper mapper) {
        this.todoListRepository = todoListRepository;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    private Collection<TodoListViewModel> getAllTodoLists(){
        List<TodoList> todoLists = this.todoListRepository.findAll();
        List<TodoListViewModel> todoListViewModels = todoLists.stream()
                .map(todoList -> this.mapper.convertToTodoListViewModel(todoList))
                .collect(Collectors.toList());
        return todoListViewModels;


    }

    @PostMapping
    public TodoList save(@RequestBody TodoListViewModel todoListViewModel, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ValidationException();
        }

        TodoList todoList = mapper.convertToTodoList(todoListViewModel);
        this.todoListRepository.save(todoList);
        return todoList;
    }

    @DeleteMapping("/{id}")
    public void deleteTodoList(@PathVariable Long id){
        todoListRepository.deleteById(id);
    }
}
