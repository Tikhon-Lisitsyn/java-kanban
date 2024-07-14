package API.java.com.yandex.kanban.http_handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.com.yandex.kanban.model.Epic;
import main.java.com.yandex.kanban.service.TaskManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class EpicHandler extends BaseHttpHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public EpicHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();
        String[] splitPath = path.split("/");

        switch (method) {
            case GET:
                if (splitPath.length == 2) {
                    sendText(httpExchange, gson.toJson(taskManager.getAllEpics()));
                } else if (splitPath.length == 3) {
                    int id = Integer.parseInt(splitPath[2]);
                    Epic epic = taskManager.getEpicById(id);
                    if (epic != null) {
                        sendText(httpExchange, gson.toJson(epic));
                    } else {
                        sendErrorResponse(httpExchange, 404, "Эпик не найден.");
                    }
                }
                break;
            case POST:
                if (splitPath.length == 2) {
                    try (Reader reader = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8)) {
                        Epic epic = gson.fromJson(reader, Epic.class);
                        if (epic.getId() == 0) {
                            taskManager.addEpic(epic);
                        } else {
                            taskManager.updateEpic(epic, epic.getId());
                        }
                        httpExchange.sendResponseHeaders(201, 0);
                    } catch (JsonSyntaxException e) {
                        sendErrorResponse(httpExchange, 400, "Некорректный JSON формат.");
                    }
                }
                break;
            case DELETE:
                if (splitPath.length == 3) {
                    int id = Integer.parseInt(splitPath[2]);
                    taskManager.removeEpicById(id);
                    httpExchange.sendResponseHeaders(204, 0);
                }
                break;
            default:
                sendErrorResponse(httpExchange, 404, "Not found");
        }
    }
    private void sendErrorResponse(HttpExchange httpExchange, int statusCode, String message) throws IOException {
        String response = gson.toJson(new ErrorResponse(statusCode, message));
        httpExchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private static class ErrorResponse {
        private final int statusCode;
        private final String message;

        public ErrorResponse(int statusCode, String message) {
            this.statusCode = statusCode;
            this.message = message;
        }
    }
}