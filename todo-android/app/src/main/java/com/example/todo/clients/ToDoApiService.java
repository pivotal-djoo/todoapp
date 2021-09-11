package com.example.todo.clients;

import com.example.todo.models.ToDo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ToDoApiService {
    @GET("todos")
    Call<List<ToDo>> getToDos();

    @POST("todos")
    Call<ToDo> addToDo(@Body ToDo todo);

    @PUT("todos")
    Call<ToDo> updateToDo(@Body ToDo todo);

    @DELETE("todos/{id}")
    Call<Void> deleteToDo(@Path("id") long id);
}
