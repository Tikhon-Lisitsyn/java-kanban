package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager historyManager;
    private Task task;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task = new Task();
    }

    @Test
    void addTaskToHistory() {
        assertTrue(historyManager.getHistory().isEmpty(),"История не пуста!");
        historyManager.add(task);
        assertTrue(historyManager.getHistory().contains(task), "Задача не добавлена в историю!");
    }

    @Test
    void removeTaskFromHistory() {
        historyManager.add(task);
        historyManager.remove(task.getId());
        assertFalse(historyManager.getHistory().contains(task), "Задача не удалена из истории!");
    }

    @Test
    void removeNonExistentTask() {
        int nonExistentTaskId = 999;
        historyManager.remove(nonExistentTaskId);
    }

    @Test
    void removeSingleTaskFromHistory() {
        historyManager.add(task);
        historyManager.remove(task.getId());
        assertTrue(historyManager.getHistory().isEmpty(), "Задача не удалена из истории!");
    }
}