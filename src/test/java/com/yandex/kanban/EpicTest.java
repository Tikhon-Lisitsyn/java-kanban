package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.service.TaskStatus;
import main.java.com.yandex.kanban.model.Epic;
import main.java.com.yandex.kanban.model.Subtask;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    @Test
    void epic1AndEpic2EqualsId() {
        Epic epic1 = new Epic();
        epic1.setId(1);
        Epic epic2 = new Epic();
        epic2.setId(1);

        assertEquals(epic1, epic2, "Объекты не равны!");
        assertEquals(epic1.hashCode(), epic2.hashCode(), "Хеш-коды не равны!");
        assertEquals(epic1.getId(), epic2.getId(), "Id двух эпиков не равны!");
    }


    @Test
    void addSubtaskToEpic() {
        Epic epic = new Epic();
        Subtask subtask = new Subtask();
        epic.getIdOfSubtasks().add(subtask.getId());

        assertTrue(epic.getIdOfSubtasks().contains(subtask.getId()), "Подзадача не добавлена в эпик!");
    }

    @Test
    void updateEpicStatusBasedOnSubtasks() {
        Epic epic = new Epic();

        Subtask subtask1 = new Subtask();
        Subtask subtask2 = new Subtask();
        Subtask subtask3 = new Subtask();

        subtask1.setStatus(TaskStatus.NEW);
        subtask2.setStatus(TaskStatus.NEW);
        subtask3.setStatus(TaskStatus.NEW);

        epic.getIdOfSubtasks().add(subtask1.getId());
        epic.getIdOfSubtasks().add(subtask2.getId());
        epic.getIdOfSubtasks().add(subtask3.getId());
        epic.setStatus(TaskStatus.NEW);

        assertEquals(TaskStatus.NEW, epic.getStatus(), "Статус эпика должен быть NEW!");

        subtask1.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        subtask3.setStatus(TaskStatus.DONE);
        epic.setStatus(TaskStatus.DONE);

        assertEquals(TaskStatus.DONE, epic.getStatus(), "Статус эпика должен быть DONE!");

        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        subtask2.setStatus(TaskStatus.IN_PROGRESS);
        subtask3.setStatus(TaskStatus.IN_PROGRESS);
        epic.setStatus(TaskStatus.IN_PROGRESS);

        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS!");
    }
}