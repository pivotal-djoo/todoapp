package com.example.todo.models;

public class ToDo {
    private String text;

    public ToDo(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToDo)) return false;

        ToDo toDo = (ToDo) o;

        return text != null ? text.equals(toDo.text) : toDo.text == null;
    }

    @Override
    public int hashCode() {
        return text != null ? text.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "text='" + text + '\'' +
                '}';
    }
}
