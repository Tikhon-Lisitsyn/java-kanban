package API.java.com.yandex.kanban;

import com.sun.net.httpserver.HttpServer;
import API.java.com.yandex.kanban.http_handlers.*;
import main.java.com.yandex.kanban.service.HistoryManager;
import main.java.com.yandex.kanban.service.Managers;
import main.java.com.yandex.kanban.service.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private final HttpServer server;
    private final TaskManager taskManager;
    private final HistoryManager historyManager;

    public HttpTaskServer() throws IOException {
        taskManager = Managers.getDefault();
        historyManager = Managers.getDefaultHistory();
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/tasks", new TaskHandler(taskManager));
        server.createContext("/subtasks", new SubtaskHandler(taskManager));
        server.createContext("/epics", new EpicHandler(taskManager));
        server.createContext("/history", new HistoryHandler(historyManager));
        server.createContext("/prioritized", new PrioritizedHandler(taskManager));
    }

    public void start() {
        server.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }

    public void stop() {
        server.stop(0);
        System.out.println("HTTP-сервер остановлен");
    }

    public static void main(String[] args) throws IOException {
        HttpTaskServer server = new HttpTaskServer();
        server.start();
    }
}