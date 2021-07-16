package com.example.todo;

import com.example.todo.models.ToDo;

import java.util.List;

import static java.util.Arrays.asList;

public class Persistence {
    public List<ToDo> getSavedTodos() {
        return asList(
                new ToDo("Pick up peanuts"),
                new ToDo("Pick up cheese"),
                new ToDo("Pick up eggs")
        );
    }
}
