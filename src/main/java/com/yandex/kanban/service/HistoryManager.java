package main.java.com.yandex.kanban.service;

import main.java.com.yandex.kanban.model.Task;

import java.util.List;

public interface HistoryManager {
    public List<Task> getHistory();
    public void add(Task task);
    public void remove(int id);
}
