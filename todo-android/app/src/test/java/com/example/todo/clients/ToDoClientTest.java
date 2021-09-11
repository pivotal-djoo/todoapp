package com.example.todo.clients;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.todo.models.ToDo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ToDoClientTest {

    private MockWebServer mockWebServer = new MockWebServer();
    private ToDoClient subject;

    @Before
    public void setup() throws IOException {
        mockWebServer.start(8080);
        subject = new ToDoClient(mockWebServer.url("/").toString());
    }

    @Test
    public void getToDos_shouldReturnToDosList() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<String> future = new CompletableFuture<>();
        MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setBody("[{\"id\":1,\"text\":\"Pick up pickles\",\"checked\":false},{\"id\":2,\"text\":\"Pick up kids\",\"checked\":true}]");
        mockWebServer.enqueue(response);

        subject.getToDos(new ToDosResponseCallback<List<ToDo>>() {
            @Override
            public void onResponse(List<ToDo> todo) {
                assertThat(todo).hasSize(2);
                assertThat(todo.get(0).getText()).isEqualTo("Pick up pickles");
                assertThat(todo.get(0).isChecked()).isFalse();
                assertThat(todo.get(1).getText()).isEqualTo("Pick up kids");
                assertThat(todo.get(1).isChecked()).isTrue();
                future.complete("Successful");
            }

            @Override
            public void onFailure() {
            }
        });

        RecordedRequest recordedRequest = mockWebServer.takeRequest(2, TimeUnit.SECONDS);
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");
        assertThat(recordedRequest.getPath()).isEqualTo("/todos");
        assertThat(future.get(2, TimeUnit.SECONDS)).isEqualTo("Successful");
    }

    @Test
    public void addToDo() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<String> future = new CompletableFuture<>();
        MockResponse response = new MockResponse();
        response.setResponseCode(201);
        response.setBody("{\"id\":1,\"text\":\"Pick up pickles\",\"checked\":false}");
        mockWebServer.enqueue(response);

        ToDo newToDo = new ToDo("Pick up pickles");

        subject.addToDo(newToDo, new ToDosResponseCallback<ToDo>() {
            @Override
            public void onResponse(ToDo todo) {
                assertThat(todo.getText()).isEqualTo("Pick up pickles");
                assertThat(todo.isChecked()).isFalse();
                future.complete("Successful");
            }

            @Override
            public void onFailure() {
            }
        });

        RecordedRequest recordedRequest = mockWebServer.takeRequest(2, TimeUnit.SECONDS);
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
        assertThat(recordedRequest.getPath()).isEqualTo("/todos");
        assertThat(recordedRequest.getBody().toString()).contains("{\"text\":\"Pick up pickles\",\"checked\":false}");
        assertThat(future.get(2, TimeUnit.SECONDS)).isEqualTo("Successful");
    }

    @Test
    public void updateToDo() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<String> future = new CompletableFuture<>();
        MockResponse response = new MockResponse();
        response.setResponseCode(200);
        response.setBody("{\"id\":1,\"text\":\"Pick up pickles\",\"checked\":false}");
        mockWebServer.enqueue(response);

        ToDo updatedToDo = new ToDo("Pick up pickles");

        subject.updateToDo(updatedToDo, new ToDosResponseCallback<ToDo>() {
            @Override
            public void onResponse(ToDo todo) {
                assertThat(todo.getText()).isEqualTo("Pick up pickles");
                assertThat(todo.isChecked()).isFalse();
                future.complete("Successful");
            }

            @Override
            public void onFailure() {
            }
        });

        RecordedRequest recordedRequest = mockWebServer.takeRequest(2, TimeUnit.SECONDS);
        assertThat(recordedRequest.getMethod()).isEqualTo("PUT");
        assertThat(recordedRequest.getPath()).isEqualTo("/todos");
        assertThat(recordedRequest.getBody().toString()).contains("{\"text\":\"Pick up pickles\",\"checked\":false}");
        assertThat(future.get(2, TimeUnit.SECONDS)).isEqualTo("Successful");
    }

    @Test
    public void deleteToDo() throws InterruptedException, ExecutionException, TimeoutException {
        CompletableFuture<String> future = new CompletableFuture<>();
        MockResponse response = new MockResponse();
        response.setResponseCode(200);
        mockWebServer.enqueue(response);

        ToDo deletedToDo = new ToDo(1L,"Pick up pickles", false);

        subject.deleteToDo(deletedToDo, new ToDosResponseCallback<Void>() {
            @Override
            public void onResponse(Void todo) {
                future.complete("Successful");
            }

            @Override
            public void onFailure() {
            }
        });

        RecordedRequest recordedRequest = mockWebServer.takeRequest(2, TimeUnit.SECONDS);
        assertThat(recordedRequest.getMethod()).isEqualTo("DELETE");
        assertThat(recordedRequest.getPath()).isEqualTo("/todos/1");
        assertThat(future.get(2, TimeUnit.SECONDS)).isEqualTo("Successful");
    }

    @After
    public void shutdown() throws IOException {
        mockWebServer.shutdown();
    }
}
