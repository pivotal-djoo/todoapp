package com.example.todo;

import android.content.Context;

import com.example.todo.clients.ToDoClient;
import com.example.todo.clients.ToDosResponseCallback;
import com.example.todo.models.ToDo;

import java.util.List;

public class MainPresenter {
    private MainView view;
    private ToDoClient toDoClient;

    public MainPresenter(MainView view, ToDoClient toDoClient) {
        this.view = view;
        this.toDoClient = toDoClient;
    }

    public void fetchList(Context context) {
        toDoClient.getToDos(new ToDosResponseCallback<List<ToDo>>() {
            @Override
            public void onResponse(List<ToDo> todo) {
                view.refreshToDos(todo);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    public void addToDo(Context context, ToDo newToDo) {
        toDoClient.addToDo(newToDo, new ToDosResponseCallback<ToDo>() {
            @Override
            public void onResponse(ToDo todo) {
                fetchList(context);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    public void checkToDo(Context context, ToDo toDo) {
        toDo.setChecked(true);
        toDoClient.updateToDo(toDo, new ToDosResponseCallback() {
            @Override
            public void onResponse(Object todo) {
                fetchList(context);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    public void deleteToDo(Context context, ToDo toDo) {
        toDoClient.deleteToDo(toDo, new ToDosResponseCallback() {
            @Override
            public void onResponse(Object todo) {
                fetchList(context);
            }

            @Override
            public void onFailure() {
            }
        });
    }
}
