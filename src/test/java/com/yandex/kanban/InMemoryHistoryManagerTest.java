package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.InMemoryHistoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    private InMemoryHistoryManager historyManager;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void addTaskToHistory() {
        assertTrue(historyManager.getHistory().isEmpty(),"История не пуста!");
        Task task = new Task();
        historyManager.add(task);
        assertTrue(historyManager.getHistory().contains(task), "Задача не добавлена в историю!");
    }

    @Test
    void removeTaskFromHistory() {
        Task task = new Task();
        historyManager.add(task);
        historyManager.remove(task.getId());
        assertFalse(historyManager.getHistory().contains(task), "Задача не удалена из истории!");
    }

    @Test
    void removeNonExistentTask() {
        int nonExistentTaskId = 999;
        historyManager.remove(nonExistentTaskId);
        // Проверяем, что никаких исключений не возникло
    }

    @Test
    void removeSingleTaskFromHistory() {
        Task task = new Task();
        historyManager.add(task);
        historyManager.remove(task.getId());
        assertTrue(historyManager.getHistory().isEmpty(), "Задача не удалена из истории!");
    }
}