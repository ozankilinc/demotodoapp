package com.ozan.demotodoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class TodoList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TodoItem> items = new ArrayList<>();

    public TodoList() {
    }

    public TodoList(String name) {
        this.name = name;
    }

    public int getTodoListofItems(){
        return this.items.size();
    }

    public void addTodoItem(TodoItem todoItem){
        todoItem.setTodoList(this);
        items.add(todoItem);
    }


}
