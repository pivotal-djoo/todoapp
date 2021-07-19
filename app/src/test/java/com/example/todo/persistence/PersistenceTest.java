package com.example.todo.persistence;

import com.example.todo.models.ToDo;

import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PersistenceTest {

    private PersistenceProvider mockProvider;
    private Persistence subject;

    @Before
    public void setUp() throws Exception {
        mockProvider = mock(PersistenceProvider.class);
        subject = new Persistence(mockProvider);
        when(mockProvider.getString("todos"))
                .thenReturn("[{\"text\":\"Pick up pickles\"}]");
    }

    @Test
    public void getSavedTodos_callsPersistenceProvider() {
        assertThat(subject.getSavedTodos())
                .isEqualTo(singletonList(new ToDo("Pick up pickles")));
    }

    @Test
    public void addToDo_callsPersistenceProvider() {
        subject.addToDo(new ToDo("Pick up kids"), null);
        verify(mockProvider)
                .storeString(
                        null,
                        "todos",
                        "[{\"text\":\"Pick up pickles\"},{\"text\":\"Pick up kids\"}]");
    }
}