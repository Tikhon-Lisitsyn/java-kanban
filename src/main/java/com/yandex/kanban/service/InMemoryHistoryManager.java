package main.java.com.yandex.kanban.service;

import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.HistoryManager;
import java.util.ArrayList;
import java.util.List;

class InMemoryHistoryManager implements HistoryManager {

    protected final List<Task> viewedTasksList;
    public InMemoryHistoryManager() {
        viewedTasksList = new ArrayList<>();
    }

    @Override
    public List<Task> getHistory() {
        return viewedTasksList;
    }

    @Override
    public void add(Task task) {
        viewedTasksList.add(task);
    }
}
