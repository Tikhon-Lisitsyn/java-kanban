package API.java.com.yandex.kanban.http_handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.TaskManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class TaskHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public TaskHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();
        String[] splitPath = path.split("/");

        switch (method) {
            case "GET":
                if (splitPath.length == 2) {
                    sendText(httpExchange, gson.toJson(taskManager.getAllTasks()));
                } else if (splitPath.length == 3) {
                    int id = Integer.parseInt(splitPath[2]);
                    Task task = taskManager.getTaskById(id);
                    if (task != null) {
                        sendText(httpExchange, gson.toJson(task));
                    } else {
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                }
                break;
            case "POST":
                if (splitPath.length == 2) {
                    try (Reader reader = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8)) {
                        Task task = gson.fromJson(reader, Task.class);
                        if (task.getId() == 0) {
                            taskManager.addTask(task);
                        } else {
                            taskManager.updateTask(task, task.getId());
                        }
                        httpExchange.sendResponseHeaders(201, 0);
                    } catch (JsonSyntaxException e) {
                        httpExchange.sendResponseHeaders(400, 0);
                    }
                }
                break;
            case "DELETE":
                if (splitPath.length == 3) {
                    int id = Integer.parseInt(splitPath[2]);
                    taskManager.removeTaskById(id);
                    httpExchange.sendResponseHeaders(204, 0);
                }
                break;
            default:
                httpExchange.sendResponseHeaders(404, 0);
        }
    }
}