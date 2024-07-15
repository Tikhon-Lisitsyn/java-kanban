package API.java.com.yandex.kanban.http_handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.java.com.yandex.kanban.service.HistoryManager;
import main.java.com.yandex.kanban.service.TaskManager;

import java.io.IOException;

public class HistoryHandler extends BaseHttpHandler implements HttpHandler {
    private final HistoryManager historyManager;
    private final Gson gson;

    public HistoryHandler(HistoryManager historyManager) {
        this.historyManager = historyManager;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        if (GET.equals(method)) {
            sendText(httpExchange, gson.toJson(historyManager.getHistory()));
        } else {
            httpExchange.sendResponseHeaders(404, 0);
        }
    }
}