package com.example.todo.models;

import java.util.Objects;

public class ToDo {
    private Long id;
    private String text;
    private boolean checked;

    public ToDo(String text) {
        this.text = text;
    }

    public ToDo(Long id, String text, boolean checked) {
        this.id = id;
        this.text = text;
        this.checked = checked;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToDo toDo = (ToDo) o;
        return checked == toDo.checked && Objects.equals(id, toDo.id) && Objects.equals(text, toDo.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, checked);
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", checked=" + checked +
                '}';
    }
}
