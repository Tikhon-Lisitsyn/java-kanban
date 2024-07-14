package test.java.com.yandex.kanban.API;

import API.java.com.yandex.kanban.HttpTaskServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.InMemoryTaskManager;
import main.java.com.yandex.kanban.service.TaskManager;
import main.java.com.yandex.kanban.service.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HttpTaskServerPrioritizedTest {

    private TaskManager manager;
    private HttpTaskServer taskServer;
    private Gson gson;

    @BeforeEach
    public void setUp() throws IOException {
        manager = new InMemoryTaskManager();
        taskServer = new HttpTaskServer();
        gson = GsonUtil.createGson();
        taskServer.start();
    }

    @Test
    public void testGetPrioritizedTasks() throws IOException, InterruptedException {
        Task task1 = new Task();
        task1.setName("Task 1");
        task1.setDescription("Description 1");
        task1.setStatus(TaskStatus.NEW);
        task1.setDuration(Duration.ofMinutes(5));
        task1.setStartTime(LocalDateTime.now());

        Task task2 = new Task();
        task2.setName("Task 2");
        task2.setDescription("Description 2");
        task2.setStatus(TaskStatus.NEW);
        task2.setDuration(Duration.ofMinutes(5));
        task2.setStartTime(LocalDateTime.now().plusMinutes(10));

        manager.addTask(task1);
        manager.addTask(task2);

        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());

        List<Task> prioritizedTasks = gson.fromJson(response.body(), new TypeToken<List<Task>>(){}.getType());
        assertNotNull(prioritizedTasks, "Список задач по приоритету не возвращается");
    }
}