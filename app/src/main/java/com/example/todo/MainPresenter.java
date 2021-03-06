package com.example.todo;

import android.content.Context;

import com.example.todo.models.ToDo;
import com.example.todo.persistence.Persistence;

import java.util.List;

public class MainPresenter {
    private MainView view;
    private Persistence persistence;

    public MainPresenter(MainView view, Persistence persistence) {
        this.view = view;
        this.persistence = persistence;
    }

    public List<ToDo> fetchList(Context context) {
        return persistence.getSavedTodos(context);
    }

    public void addToDo(Context context, ToDo newItem) {
        persistence.addToDo(context, newItem);
        view.refreshToDos();
    }
}
