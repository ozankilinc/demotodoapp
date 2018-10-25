package com.ozan.demotodoapp.controller;

import com.ozan.demotodoapp.model.TodoItem;
import com.ozan.demotodoapp.model.TodoList;
import com.ozan.demotodoapp.repository.TodoItemRepository;
import com.ozan.demotodoapp.repository.TodoListRepository;
import com.ozan.demotodoapp.util.Mapper;
import com.ozan.demotodoapp.viewModel.TodoItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todoitems")
public class TodoItemController {

    private TodoListRepository todoListRepository;
    private TodoItemRepository todoItemRepository;
    private Mapper  mapper;


    public TodoItemController(TodoListRepository todoListRepository, TodoItemRepository todoItemRepository, Mapper mapper) {
        this.todoListRepository = todoListRepository;
        this.todoItemRepository = todoItemRepository;
        this.mapper = mapper;
    }

    @GetMapping("/all")
    public List<TodoItemViewModel> getAllTodoItems(){
        List<TodoItem> todoItems = this.todoItemRepository.findAll();

        List<TodoItemViewModel> todoItemViewModels = todoItems.stream().
                map(todoItem -> this.mapper.convertToTodoItemViewModel(todoItem))
                .collect(Collectors.toList());
        return todoItemViewModels;
    }

    @GetMapping("/by/{id}")
    public TodoItemViewModel getTodeItemById(@PathVariable Long id){
        TodoItem todoItem = this.todoItemRepository.findById(id).orElse(null);
        if (todoItem == null){
            throw  new EntityNotFoundException();
        }
        TodoItemViewModel todoItemViewModel = mapper.convertToTodoItemViewModel(todoItem);
        return todoItemViewModel;

    }

    @GetMapping("/byTodoList/{todolistId}")
    public List<TodoItemViewModel> findTodoItemsInTodoList(@PathVariable Long todolistId){
        List<TodoItem> todoItems = new ArrayList<>();
        Optional<TodoList> todoList = this.todoListRepository.findById(todolistId);
        if (todoList.isPresent()){
            todoItems = todoItemRepository.findAllByTodoList(todoList.get());
        }
        List<TodoItemViewModel> todoItemViewModels = todoItems.stream().map(todoItem ->
        this.mapper.convertToTodoItemViewModel(todoItem)).collect(Collectors.toList());
        return todoItemViewModels;
    }

    @PutMapping()
    public TodoItem editTodoItem(@RequestBody TodoItemViewModel todoItemViewModel, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ValidationException();
        }
        TodoItem todoItem = this.mapper.convertToTodoItem(todoItemViewModel);
        TodoList todoList = this.todoListRepository.findById(todoItemViewModel.getTodoListId()).get();
        todoList.addTodoItem(todoItem);
        this.todoItemRepository.save(todoItem);
        this.todoListRepository.save(todoList);


        return todoItem;


    }


    @PostMapping
    public TodoItem saveTodoItem(@RequestBody TodoItemViewModel todoItemViewModel, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            throw new ValidationException();
        }
        TodoItem todoItem = this.mapper.convertToTodoItem(todoItemViewModel);
        TodoList todoList = this.todoListRepository.findById(todoItemViewModel.getTodoListId()).get();
        todoList.addTodoItem(todoItem);
        this.todoItemRepository.save(todoItem);
        this.todoListRepository.save(todoList);

        return todoItem;
    }

    @DeleteMapping("/{id}")
    public void deleteNoteById(@PathVariable Long id){
        TodoItem todoItem = this.todoItemRepository.findById(id).get();
        TodoList todoList = todoListRepository.findById(todoItem.getTodoList().getId()).get();
        todoList.getItems().remove(todoItem);
        this.todoListRepository.save(todoList);
        this.todoItemRepository.deleteById(id);
    }
}
