package com.example.todo;

import android.content.Context;

import com.example.todo.models.ToDo;
import com.example.todo.persistence.Persistence;

import java.util.List;

public class MainPresenter {
    private Persistence persistence;

    public MainPresenter(Persistence persistence) {
        this.persistence = persistence;
    }

    public List<ToDo> fetchList() {
        return persistence.getSavedTodos();
    }

    public void addToDo(ToDo newItem, Context context) {
        persistence.addToDo(newItem, context);
    }
}
