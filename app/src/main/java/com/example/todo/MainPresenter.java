package com.example.todo;

import android.content.Context;

import com.example.todo.models.ToDo;
import com.example.todo.persistence.Persistence;

import java.util.List;

public class MainPresenter {
    private MainView mainView;
    private Persistence persistence;

    public MainPresenter(MainView mainView, Persistence persistence) {
        this.mainView = mainView;
        this.persistence = persistence;
    }

    public List<ToDo> fetchList(Context context) {
        return persistence.getSavedTodos(context);
    }

    public void addToDo(Context context, ToDo newItem) {
        persistence.addToDo(context, newItem);
        mainView.refreshToDos();
    }
}
