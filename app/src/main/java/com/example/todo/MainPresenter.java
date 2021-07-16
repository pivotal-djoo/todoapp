package com.example.todo;

import com.example.todo.models.ToDo;

import java.util.List;

public class MainPresenter {
    private Persistence persistence;

    public MainPresenter(Persistence persistence) {
        this.persistence = persistence;
    }

    public List<ToDo> fetchList() {
        return persistence.getSavedTodos();
    }
}
