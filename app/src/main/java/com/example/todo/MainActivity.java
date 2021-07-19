package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.todo.models.ToDo;
import com.example.todo.persistence.Persistence;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter mainPresenter;
    private ToDoListAdapter toDosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenter(new Persistence(null));

        List<ToDo> savedToDos = mainPresenter.fetchList();

        RecyclerView listview = findViewById(R.id.recycler_view);
        toDosAdapter = new ToDoListAdapter(savedToDos);
        listview.setAdapter(toDosAdapter);
    }
}