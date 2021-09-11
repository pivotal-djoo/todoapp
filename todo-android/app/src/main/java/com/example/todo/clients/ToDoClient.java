package com.example.todo.clients;

import com.example.todo.models.ToDo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoClient {

    private final ToDoApiService toDoApiService;

    public ToDoClient(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        toDoApiService = retrofit.create(ToDoApiService.class);
    }

    public void getToDos(ToDosResponseCallback callback) {
        ToDoClientCallback toDoClientCallback = new ToDoClientCallback<List<ToDo>>(callback);
        toDoApiService.getToDos().enqueue(toDoClientCallback);
    }

    public void addToDo(ToDo newToDo, ToDosResponseCallback callback) {
        ToDoClientCallback toDoClientCallback = new ToDoClientCallback<ToDo>(callback);
        toDoApiService.addToDo(newToDo).enqueue(toDoClientCallback);
    }

    public void updateToDo(ToDo updatedToDo, ToDosResponseCallback callback) {
        ToDoClientCallback toDoClientCallback = new ToDoClientCallback<ToDo>(callback);
        toDoApiService.updateToDo(updatedToDo).enqueue(toDoClientCallback);
    }

    public void deleteToDo(ToDo deletedToDo, ToDosResponseCallback callback) {
        ToDoClientCallback toDoClientCallback = new ToDoClientCallback<Void>(callback);
        toDoApiService.deleteToDo(deletedToDo.getId()).enqueue(toDoClientCallback);
    }

    private class ToDoClientCallback<T> implements Callback<T> {
        private ToDosResponseCallback callback;

        public ToDoClientCallback(ToDosResponseCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            callback.onResponse(response.body());
        }

        @Override
        public void onFailure(Call<T> call, Throwable throwable) {
            callback.onFailure();
        }
    }
}
