package com.example.todo.persistence;

import android.content.Context;

import com.example.todo.models.ToDo;
import com.example.todo.persistence.PersistenceProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Persistence {

    private PersistenceProvider provider;

    public Persistence(PersistenceProvider persistenceProvider) {
        this.provider = persistenceProvider;
    }

    public List<ToDo> getSavedTodos() {
        Type typeToken = new TypeToken<ArrayList<ToDo>>() {}.getType();
        return new Gson().fromJson(provider.getString("todos"), typeToken);
    }

    public void addToDo(ToDo catFood, Context context) {
        Type typeToken = new TypeToken<ArrayList<ToDo>>() {}.getType();
        Gson gson = new Gson();
        List<ToDo> currentTodos = gson.fromJson(provider.getString("todos"), typeToken);
        currentTodos.add(catFood);
        provider.storeString(context, "todos", gson.toJson(currentTodos));
    }
}
