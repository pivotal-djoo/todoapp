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

    public void updateToDo(Context context, ToDo toDo) {
        String serializedCurrentToDos = persistenceProvider.getString(context, "todos");
        if (serializedCurrentToDos != null && !serializedCurrentToDos.isEmpty()) {
            List<ToDo> currentTodos = gson.fromJson(serializedCurrentToDos, typeToken);
            int index = getIndex(toDo, currentTodos);
            if (index >= 0) {
                currentTodos.set(index, toDo);
            }
            String serializedToDos = gson.toJson(currentTodos);
            persistenceProvider.storeString(context, "todos", serializedToDos);
        }
    }

    public void deleteToDo(Context context, ToDo toDo) {
        String serializedCurrentToDos = persistenceProvider.getString(context, "todos");
        if (serializedCurrentToDos != null && !serializedCurrentToDos.isEmpty()) {
            List<ToDo> currentTodos = gson.fromJson(serializedCurrentToDos, typeToken);
            int index = getIndex(toDo, currentTodos);
            if (index >= 0) {
                currentTodos.remove(index);
            }
            String serializedToDos = gson.toJson(currentTodos);
            persistenceProvider.storeString(context, "todos", serializedToDos);
        }
    }

    private int getIndex(ToDo toDo, List<ToDo> currentTodos) {
        int index = -1;
        for (int i = 0; i < currentTodos.size(); i++) {
            if (currentTodos.get(i).getText().equals(toDo.getText())) {
                index = i;
            }
        }
        return index;
    }
}
