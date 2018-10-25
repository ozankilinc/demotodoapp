package com.ozan.demotodoapp.viewModel;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class TodoItemViewModel {
    private Long id;

    private String name;
    private String description;
    private String status;
    private Instant deadline;
    private Long todoListId;
}
