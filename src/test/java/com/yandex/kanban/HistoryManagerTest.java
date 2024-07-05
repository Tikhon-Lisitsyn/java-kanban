package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.FileBackedTaskManager;
import main.java.com.yandex.kanban.service.HistoryManager;
import main.java.com.yandex.kanban.service.InMemoryHistoryManager;
import main.java.com.yandex.kanban.service.ManagerSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    private HistoryManager historyManager;
    private Task task;
    private Task task1;
    private Task task2;
    private Task task3;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task = new Task();
        task1 = new Task();
        task2 = new Task();
        task3 = new Task();
    }

    @Test
    void getHistory() {
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        task.setId(1);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void add() {
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        task.setId(1);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    void remove() {
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        task.setId(1);
        historyManager.add(task);
        historyManager.remove(1);
        assertEquals(0, historyManager.getHistory().size());
    }

    @Test
    void removeFromBeginning() {
        task1.setName("Test Task 1");
        task1.setDescription("This is a test task 1");
        task1.setStartTime(LocalDateTime.now());
        task1.setDuration(Duration.ofHours(1));
        task1.setId(1);
        historyManager.add(task1);
        task2.setName("Test Task 2");
        task2.setDescription("This is a test task 2");
        task2.setStartTime(LocalDateTime.now().plusHours(1));
        task2.setDuration(Duration.ofHours(1));
        task2.setId(2);
        historyManager.add(task2);
        historyManager.remove(1);
        assertEquals(1, historyManager.getHistory().size());
        assertEquals(2, historyManager.getHistory().getFirst().getId());
    }

    @Test
    void removeFromMiddle() {
        task1.setName("Test Task 1");
        task1.setDescription("This is a test task 1");
        task1.setStartTime(LocalDateTime.now());
        task1.setDuration(Duration.ofHours(1));
        task1.setId(1);
        historyManager.add(task1);
        task2.setName("Test Task 2");
        task2.setDescription("This is a test task 2");
        task2.setStartTime(LocalDateTime.now().plusHours(1));
        task2.setDuration(Duration.ofHours(1));
        task2.setId(2);
        historyManager.add(task2);
        task3.setName("Test Task 3");
        task3.setDescription("This is a test task 3");
        task3.setStartTime(LocalDateTime.now().plusHours(2));
        task3.setDuration(Duration.ofHours(1));
        task3.setId(3);
        historyManager.add(task3);
        historyManager.remove(2);
        assertEquals(2, historyManager.getHistory().size());
        assertEquals(1, historyManager.getHistory().get(0).getId());
        assertEquals(3, historyManager.getHistory().get(1).getId());
    }

    @Test
    void removeFromEnd() {
        task1.setName("Test Task 1");
        task1.setDescription("This is a test task 1");
        task1.setStartTime(LocalDateTime.now());
        task1.setDuration(Duration.ofHours(1));
        task1.setId(1);
        historyManager.add(task1);
        task2.setName("Test Task 2");
        task2.setDescription("This is a test task 2");
        task2.setStartTime(LocalDateTime.now().plusHours(1));
        task2.setDuration(Duration.ofHours(1));
        task2.setId(2);
        historyManager.add(task2);
        historyManager.remove(2);
        assertEquals(1, historyManager.getHistory().size());
        assertEquals(1, historyManager.getHistory().getFirst().getId());
    }

    @Test
    void duplicateTasks() {
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        task.setId(1);
        historyManager.add(task);
        historyManager.add(task);
        assertEquals(1, historyManager.getHistory().size());
    }
}