package com.example.todo.persistence;

import com.example.todo.models.ToDo;

import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersistenceTest {

    private PersistenceProvider mockProvider;
    private Persistence subject;

    @Before
    public void setUp() {
        mockProvider = mock(PersistenceProvider.class);
        subject = new Persistence(mockProvider);

    }

    @Test
    public void getSavedToDos_callsPersistenceProvider() {
        when(mockProvider.getString(null, "todos"))
                .thenReturn("[{\"text\":\"Pick up pickles\"}]");

        assertThat(subject.getSavedTodos(null))
                .isEqualTo(singletonList(new ToDo("Pick up pickles")));
    }

    @Test
    public void getSavedToDos_noSavedToDos_returnsEmptyList() {
        when(mockProvider.getString(null, "todos"))
                .thenReturn(null);

        assertThat(subject.getSavedTodos(null))
                .isEqualTo(emptyList());
    }

    @Test
    public void addToDo_callsPersistenceProvider() {
        when(mockProvider.getString(null, "todos"))
                .thenReturn("[{\"text\":\"Pick up pickles\"}]");

        subject.addToDo(null, new ToDo("Pick up kids"));

        verify(mockProvider)
                .storeString(
                        null,
                        "todos",
                        "[{\"text\":\"Pick up pickles\",\"checked\":false},{\"text\":\"Pick up kids\",\"checked\":false}]");
    }

    @Test
    public void addToDo_noSavedToDos() {
        subject.addToDo(null, new ToDo("Pick up kids"));

        verify(mockProvider)
                .storeString(
                        null,
                        "todos",
                        "[{\"text\":\"Pick up kids\",\"checked\":false}]");
    }
}