package com.example.todo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.todo.models.ToDo;
import com.example.todo.persistence.Persistence;
import com.example.todo.persistence.PersistenceProviderImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView, ToDoListAdapter.Callback {

    private MainPresenter mainPresenter;
    private ToDoListAdapter toDosAdapter;
    private EditText addToDoEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenter(this, new Persistence(new PersistenceProviderImpl()));

        List<ToDo> savedToDos = mainPresenter.fetchList(this);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        toDosAdapter = new ToDoListAdapter(this);
        toDosAdapter.setData(savedToDos);
        recyclerView.setAdapter(toDosAdapter);

        addToDoEditText = findViewById(R.id.new_to_do_edittext);
        Button addToDoButton = findViewById(R.id.add_to_do_button);
        addToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAddToDoButtonClick();
            }
        });
    }

    @Override
    public void refreshToDos() {
        List<ToDo> savedToDos = mainPresenter.fetchList(this);
        toDosAdapter.setData(savedToDos);
        toDosAdapter.notifyDataSetChanged();
        addToDoEditText.getText().clear();
    }

    @Override
    public void deleteTapped(ToDo todo) {
        Log.d("MainActivity", "######### delete tapped");
    }

    @Override
    public void checkboxTapped(ToDo todo, boolean checked) {
        Log.d("MainActivity", "######### checkbox checked: " + checked);
    }

    private void handleAddToDoButtonClick() {
        ToDo newToDo = new ToDo(addToDoEditText.getText().toString());
        mainPresenter.addToDo(this, newToDo);
    }
}
