package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.service.HistoryManager;
import main.java.com.yandex.kanban.service.Managers;
import main.java.com.yandex.kanban.service.TaskManager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void taskManagerNotNull() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "TaskManager не должен равняться null!");
    }

    @Test
    void historyManagerNotNull() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "HistoryManager не должен равняться null!");
    }
}