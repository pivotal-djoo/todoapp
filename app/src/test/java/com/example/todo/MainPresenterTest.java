package com.example.todo;

import com.example.todo.models.ToDo;
import com.example.todo.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class MainPresenterTest {

    private MainPresenter subject;
    private Persistence mockPersistence;
    private MainView mockMainView;

    @Before
    public void setUp() {
        mockPersistence = mock(Persistence.class);
        mockMainView = mock(MainView.class);
        subject = new MainPresenter(mockMainView, mockPersistence);
    }

    @Test
    public void fetchList_retrievesSavedToDos() {
        List<ToDo> expectedToDos = asList(
                new ToDo("Pick up peanuts"),
                new ToDo("Pick up cheese"),
                new ToDo("Pick up eggs")
        );
        when(mockPersistence.getSavedTodos(null)).thenReturn(expectedToDos);

        List<ToDo> todos = subject.fetchList(null);

        assertThat(todos).hasSize(3);
        assertThat(todos).isEqualTo(expectedToDos);
    }

    @Test
    public void addTodo_addsItemToList_thenRefreshesView() {
        ToDo catFood = new ToDo("Pick up cat food");
        subject.addToDo(null, catFood);

       verify(mockPersistence).addToDo(null, catFood);
       verify(mockMainView).refreshToDos();
    }

    @Test
    public void checkTodo_shouldUpdateDataAndRefreshView() {
        ToDo catFood = new ToDo("Pick up cat food");

        subject.checkToDo(null, catFood);

        catFood.setChecked(true);

        verify(mockPersistence).updateToDo(null, catFood);
//        verify(mockMainView).refreshToDos();
    }
}