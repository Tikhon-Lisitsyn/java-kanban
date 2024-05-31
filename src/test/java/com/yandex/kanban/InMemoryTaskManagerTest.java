package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.model.Epic;
import main.java.com.yandex.kanban.model.Subtask;
import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.InMemoryTaskManager;
import main.java.com.yandex.kanban.service.TaskStatus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Test
    void inMemoryTaskManagerAddsAllTypesOfTasksAndFindById() {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        Task task = new Task();
        inMemoryTaskManager.addTask(task);

        Epic epic = new Epic();
        inMemoryTaskManager.addEpic(epic);

        Subtask subtask = new Subtask();
        inMemoryTaskManager.addSubtask(subtask, epic.getId());

        assertNotNull(inMemoryTaskManager.getTaskById(task.getId()));
        assertNotNull(inMemoryTaskManager.getEpicById(epic.getId()));
        assertNotNull(inMemoryTaskManager.getSubtaskById(subtask.getId()));
        assertEquals(1, inMemoryTaskManager.getAllTasks().size());
        assertEquals(1, inMemoryTaskManager.getAllEpics().size());
        assertEquals(1, inMemoryTaskManager.getAllSubtasks().size());
    }

    @Test
    void tasksWithAGivenIdAndAGeneratedIdDoNotConflict() {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task = new Task();
        Task taskWithId = new Task();

        taskWithId.setId(123);
        inMemoryTaskManager.addTask(taskWithId);

        assertNotEquals(task.getId(), taskWithId.getId(), "Id задач должны быть разными");
    }

    @Test
    void CheckingWhetherATaskIsUnchangedWhenAdded() {

        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task originalTask = new Task();

        originalTask.setName("Первоначальное имя");
        originalTask.setDescription("Первоначальное описание");
        originalTask.setStatus(TaskStatus.NEW);

        int originalId = inMemoryTaskManager.addTask(originalTask).getId();
        Task updatedTask = new Task();

        updatedTask.setName("Обновленное имя");
        updatedTask.setDescription("Обновленное описание");
        updatedTask.setStatus(TaskStatus.DONE);

        inMemoryTaskManager.updateTask(updatedTask, originalId);
        Task retrievedTask = inMemoryTaskManager.getTaskById(originalId);

        assertEquals(originalTask.getName(), "Первоначальное имя", "Имя задачи должно остаться неизменным");
        assertEquals(originalTask.getDescription(), "Первоначальное описание", "Описание задачи должно остаться неизменным");
        assertEquals(originalTask.getStatus(), retrievedTask.getStatus(), "Статус задачи должен остаться неизменным");
    }
}