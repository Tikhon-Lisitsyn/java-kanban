package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.model.Subtask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    void subtask1AndSubtask2EqualsId() {
        Subtask subtask1=new Subtask();
        subtask1.setId(1);
        Subtask subtask2=new Subtask();
        subtask2.setId(1);

        assertEquals(subtask1,subtask2,"Объекты не равны!");
        assertEquals(subtask1.hashCode(),subtask2.hashCode(),"Хеш-коды не равны!");
        assertEquals(subtask1.getId(),subtask2.getId(),"Id двух подзадач не равны!");
    }
}