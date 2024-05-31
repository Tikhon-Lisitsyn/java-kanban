package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.model.Task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void task1AndTask2EqualsId() {

        Task task1 = new Task();
        task1.setId(1);

        Task task2 = new Task();
        task2.setId(1);

        assertEquals(task1, task2, "Объекты не равны!");
        assertEquals(task1.hashCode(), task2.hashCode(), "Хеш-коды не равны!");
        assertEquals(task1.getId(), task2.getId(), "Id двух задач не равны!");
    }
}