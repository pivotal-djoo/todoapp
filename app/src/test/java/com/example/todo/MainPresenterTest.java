package com.example.todo;

import com.example.todo.models.ToDo;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class MainPresenterTest {

    private MainPresenter subject;
    private Persistence mockPersistence;

    @Before
    public void setUp() throws Exception {
        mockPersistence = mock(Persistence.class);
        subject = new MainPresenter(mockPersistence);
    }

    @Test
    public void fetchList_retrievesSavedToDos() {
        List<ToDo> expectedToDos = asList(
                new ToDo("Pick up peanuts"),
                new ToDo("Pick up cheese"),
                new ToDo("Pick up eggs")
        );
        when(mockPersistence.getSavedTodos()).thenReturn(expectedToDos);

        List<ToDo> todos = subject.fetchList();

        assertThat(todos).hasSize(3);
        assertThat(todos).isEqualTo(expectedToDos);
    }
}