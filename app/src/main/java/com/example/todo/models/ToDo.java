package com.example.todo.models;

public class ToDo {
    private String text;
    private boolean checked;

    public ToDo(String text) {
        this.text = text;
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
        if (!(o instanceof ToDo)) return false;

        ToDo toDo = (ToDo) o;

        if (checked != toDo.checked) return false;
        return text != null ? text.equals(toDo.text) : toDo.text == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (checked ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "text='" + text + '\'' +
                ", checked=" + checked +
                '}';
    }
}
