package com.example.todo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static java.util.Arrays.asList;

import com.example.todo.clients.ToDoClient;
import com.example.todo.clients.ToDosResponseCallback;
import com.example.todo.models.ToDo;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;


public class MainPresenterTest {

    private MainPresenter subject;
    private MainView mockMainView;
    private ToDoClient mockToDoClient;

    @Before
    public void setUp() {
        mockMainView = mock(MainView.class);
        mockToDoClient = mock(ToDoClient.class);
        subject = new MainPresenter(mockMainView, mockToDoClient);
    }

    @Test
    public void fetchList_retrievesSavedToDos() {
        List<ToDo> expectedToDos = asList(
                new ToDo("Pick up peanuts"),
                new ToDo("Pick up cheese"),
                new ToDo("Pick up eggs")
        );

        subject.fetchList(null);

        ArgumentCaptor<ToDosResponseCallback> toDoResponseCallbackArgumentCaptor = ArgumentCaptor.forClass(ToDosResponseCallback.class);
        verify(mockToDoClient).getToDos(toDoResponseCallbackArgumentCaptor.capture());
        toDoResponseCallbackArgumentCaptor.getValue().onResponse(expectedToDos);
        verify(mockMainView).refreshToDos(expectedToDos);
    }

    @Test
    public void addTodo_addsItemToList_thenRefreshesView() {
        ToDo catFood = new ToDo("Pick up cat food");

        subject.addToDo(null, catFood);

        ArgumentCaptor<ToDosResponseCallback> toDoResponseCallbackArgumentCaptor = ArgumentCaptor.forClass(ToDosResponseCallback.class);
        verify(mockToDoClient).addToDo(eq(catFood), toDoResponseCallbackArgumentCaptor.capture());
        toDoResponseCallbackArgumentCaptor.getValue().onResponse(catFood);
        verify(mockToDoClient).getToDos(any());
    }

    @Test
    public void checkTodo_updatesToDoItem_thenRefreshesView() {
        ToDo catFood = new ToDo("Pick up cat food");

        subject.checkToDo(null, catFood);

        ToDo expectedCatFood = new ToDo("Pick up cat food");
        expectedCatFood.setChecked(true);

        ArgumentCaptor<ToDosResponseCallback> toDoResponseCallbackArgumentCaptor = ArgumentCaptor.forClass(ToDosResponseCallback.class);
        verify(mockToDoClient).updateToDo(eq(expectedCatFood), toDoResponseCallbackArgumentCaptor.capture());
        toDoResponseCallbackArgumentCaptor.getValue().onResponse(expectedCatFood);
        verify(mockToDoClient).getToDos(any());
    }

    @Test
    public void deleteTodo_removesToDoItem_thenRefreshesView() {
        ToDo catFood = new ToDo("Pick up cat food");

        subject.deleteToDo(null, catFood);

        ArgumentCaptor<ToDosResponseCallback> toDoResponseCallbackArgumentCaptor = ArgumentCaptor.forClass(ToDosResponseCallback.class);
        verify(mockToDoClient).deleteToDo(eq(catFood), toDoResponseCallbackArgumentCaptor.capture());
        toDoResponseCallbackArgumentCaptor.getValue().onResponse(null);
        verify(mockToDoClient).getToDos(any());
    }
}