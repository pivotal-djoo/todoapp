package com.example.todo.persistence;

import android.content.Context;

import com.example.todo.models.ToDo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

public class Persistence {

    private final PersistenceProvider persistenceProvider;
    private final Gson gson = new Gson();
    private final Type typeToken = new TypeToken<ArrayList<ToDo>>() {}.getType();

    public Persistence(PersistenceProvider persistenceProvider) {
        this.persistenceProvider = persistenceProvider;
    }

    public List<ToDo> getSavedTodos(Context context) {
        String serializedToDos = persistenceProvider.getString(context, "todos");

        if (serializedToDos == null) {
            return emptyList();
        }
        return gson.fromJson(serializedToDos, typeToken);
    }

    public void addToDo(Context context, ToDo newToDo) {
        String serializedCurrentToDos = persistenceProvider.getString(context, "todos");
        List<ToDo> currentTodos = new ArrayList<>();
        if (serializedCurrentToDos != null && !serializedCurrentToDos.isEmpty()) {
            currentTodos.addAll(gson.fromJson(serializedCurrentToDos, typeToken));
        }
        currentTodos.add(newToDo);
        String serializedToDos = gson.toJson(currentTodos);
        persistenceProvider.storeString(context, "todos", serializedToDos);
    }
}
