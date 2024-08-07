package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.model.Epic;
import main.java.com.yandex.kanban.model.Subtask;
import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.InMemoryTaskManager;
import main.java.com.yandex.kanban.service.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private InMemoryTaskManager inMemoryTaskManager;
    private Task task;

    @BeforeEach
    void setUp() {
        inMemoryTaskManager = new InMemoryTaskManager();
        task = new Task();
    }

    @Test
    void inMemoryTaskManagerAddsAllTypesOfTasksAndFindById() {
        task.setName("Task with auto-generated ID");
        task.setDescription("Task`s description");
        task.setDuration(Duration.ofSeconds(1));
        task.setStartTime(LocalDateTime.of(1,1,1,1,1));
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
        task.setName("Task with auto-generated ID");
        task.setDescription("Task`s description");
        task.setDuration(Duration.ofSeconds(1));
        task.setStartTime(LocalDateTime.of(1,1,1,1,1));
        inMemoryTaskManager.addTask(task);
        Task taskWithId = new Task();
        taskWithId.setName("Task with given ID");
        taskWithId.setId(123);
        taskWithId.setDescription("Task`s description");
        taskWithId.setDuration(Duration.ofSeconds(1));
        taskWithId.setStartTime(LocalDateTime.of(2,2,2,2,2));
        inMemoryTaskManager.addTask(taskWithId);
        assertNotEquals(task.getId(), taskWithId.getId(), "Id задач должны быть разными");
        assertFalse(inMemoryTaskManager.areTasksOverlapping(task.getId(), taskWithId.getId()), "Задачи с разными ID не должны конфликтовать");
    }

    @Test
    void CheckingWhetherATaskIsUnchangedWhenAdded() {
        Task originalTask = new Task();
        originalTask.setName("Первоначальное имя");
        originalTask.setDescription("Первоначальное описание");
        originalTask.setStatus(TaskStatus.NEW);
        originalTask.setDuration(Duration.ofSeconds(1));
        originalTask.setStartTime(LocalDateTime.of(1,1,1,1,1));
        int originalId = inMemoryTaskManager.addTask(originalTask).getId();
        Task updatedTask = new Task();
        updatedTask.setName("Обновленное имя");
        updatedTask.setDescription("Обновленное описание");
        updatedTask.setStatus(TaskStatus.DONE);
        updatedTask.setDuration(Duration.ofSeconds(1));
        updatedTask.setStartTime(LocalDateTime.of(2,2,2,2,2));
        inMemoryTaskManager.updateTask(updatedTask, originalId);
        Task retrievedTask = inMemoryTaskManager.getTaskById(originalId);
        assertEquals(originalTask.getStatus(), retrievedTask.getStatus(), "Статус задачи должен остаться"
                + " неизменным");
    }
}