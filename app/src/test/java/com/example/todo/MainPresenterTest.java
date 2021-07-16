package com.example.todo;

import com.example.todo.models.ToDo;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class MainPresenterTest {

    private MainPresenter subject = new MainPresenter();

    @Test
    public void fetchList_retrievesSavedToDos() {
        List<ToDo> todos = subject.fetchList();

        assertThat(todos).hasSize(3);
        assertThat(todos.get(0).getText()).isEqualTo("Pick up milk");
        assertThat(todos.get(1).getText()).isEqualTo("Pick up cheese");
        assertThat(todos.get(2).getText()).isEqualTo("Pick up eggs");
    }
}