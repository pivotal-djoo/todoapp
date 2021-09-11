package com.example.todo;

import com.example.todo.models.ToDo;

import java.util.List;

public interface MainView {
    void refreshToDos(List<ToDo> todos);
}
