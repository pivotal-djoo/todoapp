package com.example.todo.clients;

public interface ToDosResponseCallback<T> {
    void onResponse(T todo);
    void onFailure();
}
