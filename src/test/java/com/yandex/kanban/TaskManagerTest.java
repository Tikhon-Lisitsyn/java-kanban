package test.java.com.yandex.kanban;

import main.java.com.yandex.kanban.model.Epic;
import main.java.com.yandex.kanban.model.Subtask;
import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.TaskManager;
import main.java.com.yandex.kanban.service.TaskStatus;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T taskManager;

    @Test
    void addTask() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        taskManager.addTask(task);
        Assertions.assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    void getTaskById() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        taskManager.addTask(task);
        Assertions.assertNotNull(taskManager.getTaskById(1));
    }

    @Test
    void getAllTasks() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        taskManager.addTask(task);
        Assertions.assertEquals(1, taskManager.getAllTasks().size());
    }

    @Test
    void removeAllTasks() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        taskManager.addTask(task);
        taskManager.removeAllTasks();
        Assertions.assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    void removeTaskById() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        taskManager.addTask(task);
        taskManager.removeTaskById(1);
        Assertions.assertEquals(0, taskManager.getAllTasks().size());
    }

    @Test
    void updateTask() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        taskManager.addTask(task);
        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("This is an updated task");
        updatedTask.setStartTime(LocalDateTime.now().plusHours(1));
        updatedTask.setDuration(Duration.ofHours(2));
        taskManager.updateTask(updatedTask, 1);
        Assertions.assertEquals("Updated Task", taskManager.getTaskById(1).getName());
    }

    @Test
    void addSubtask() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setName("Test Subtask");
        subtask.setDescription("This is a test subtask");
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofHours(1));
        taskManager.addSubtask(subtask, 1);
        Assertions.assertEquals(1, taskManager.getAllSubtasks().size());
    }

    @Test
    void getSubtaskById() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setName("Test Subtask");
        subtask.setDescription("This is a test subtask");
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofHours(1));
        taskManager.addSubtask(subtask, 1);
        Assertions.assertNotNull(taskManager.getSubtaskById(2));
    }

    @Test
    void getAllSubtasks() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setName("Test Subtask");
        subtask.setDescription("This is a test subtask");
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofHours(1));
        taskManager.addSubtask(subtask, 1);
        Assertions.assertEquals(1, taskManager.getAllSubtasks().size());
    }

    @Test
    void removeAllSubtasks() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setName("Test Subtask");
        subtask.setDescription("This is a test subtask");
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofHours(1));
        taskManager.addSubtask(subtask, 1);
        taskManager.removeAllSubtasks();
        Assertions.assertEquals(0, taskManager.getAllSubtasks().size());
    }

    @Test
    void removeSubtaskById() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setName("Test Subtask");
        subtask.setDescription("This is a test subtask");
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofHours(1));
        taskManager.addSubtask(subtask, 1);
        taskManager.removeSubtaskById(2);
        Assertions.assertEquals(0, taskManager.getAllSubtasks().size());
    }

    @Test
    void updateSubtask() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setName("Test Subtask");
        subtask.setDescription("This is a test subtask");
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofHours(1));
        taskManager.addSubtask(subtask, 1);
        Subtask updatedSubtask = new Subtask();
        updatedSubtask.setName("Updated Subtask");
        updatedSubtask.setDescription("This is an updated subtask");
        updatedSubtask.setStartTime(LocalDateTime.now().plusHours(1));
        updatedSubtask.setDuration(Duration.ofHours(2));
        taskManager.updateSubtask(updatedSubtask, 2);
        Assertions.assertEquals("Updated Subtask", taskManager.getSubtaskById(2).getName());
    }

    @Test
    void addEpic() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Assertions.assertEquals(1, taskManager.getAllEpics().size());
    }

    @Test
    void getEpicById() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Assertions.assertNotNull(taskManager.getEpicById(1));
    }

    @Test
    void getAllEpics() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Assertions.assertEquals(1, taskManager.getAllEpics().size());
    }

    @Test
    void removeAllEpics() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        taskManager.removeAllEpics();
        Assertions.assertEquals(0, taskManager.getAllEpics().size());
    }

    @Test
    void removeEpicById() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        taskManager.removeEpicById(1);
        Assertions.assertEquals(0, taskManager.getAllEpics().size());
    }

    @Test
    void updateEpic() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Epic updatedEpic = new Epic();
        updatedEpic.setName("Updated Epic");
        updatedEpic.setDescription("This is an updated epic");
        taskManager.updateEpic(updatedEpic, 1);
        Assertions.assertEquals("Updated Epic", taskManager.getEpicById(1).getName());
    }

    @Test
    void subtasksOfEpic() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setName("Test Subtask");
        subtask.setDescription("This is a test subtask");
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofHours(1));
        taskManager.addSubtask(subtask, 1);
        Assertions.assertEquals(1, taskManager.subtasksOfEpic(1).size());
    }

    @Test
    void getTaskStatus() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        taskManager.addTask(task);
        Assertions.assertEquals(TaskStatus.NEW, taskManager.getTaskStatus(task));
    }

    @Test
    void getSubid() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setName("Test Subtask");
        subtask.setDescription("This is a test subtask");
        subtask.setStartTime(LocalDateTime.now());
        subtask.setDuration(Duration.ofHours(1));
        taskManager.addSubtask(subtask, 1);
        Assertions.assertEquals(2, taskManager.getSubid(subtask));
    }

    @Test
    void calculateEpicStatus() {
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask1 = new Subtask();
        subtask1.setName("Test Subtask 1");
        subtask1.setDescription("This is a test subtask 1");
        subtask1.setStartTime(LocalDateTime.now());
        subtask1.setDuration(Duration.ofHours(1));
        subtask1.setStatus(TaskStatus.NEW);
        taskManager.addSubtask(subtask1, 1);
        Subtask subtask2 = new Subtask();
        subtask2.setName("Test Subtask 2");
        subtask2.setDescription("This is a test subtask 2");
        subtask2.setStartTime(LocalDateTime.now().plusHours(2));
        subtask2.setDuration(Duration.ofHours(1));
        subtask2.setStatus(TaskStatus.NEW);
        taskManager.addSubtask(subtask2, 1);
        taskManager.calculateEpicStatus(epic);
        Assertions.assertEquals(TaskStatus.NEW, epic.getStatus());
    }

    @Test
    void getPrioritizedTasks() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        taskManager.addTask(task);
        Epic epic = new Epic();
        epic.setName("Test Epic");
        epic.setDescription("This is a test epic");
        taskManager.addEpic(epic);
        Subtask subtask = new Subtask();
        subtask.setName("Test Subtask");
        subtask.setDescription("This is a test subtask");
        subtask.setStartTime(LocalDateTime.now().plusHours(2));
        subtask.setDuration(Duration.ofHours(1));
        taskManager.addSubtask(subtask, 2);
        Assertions.assertEquals(3, taskManager.getPrioritizedTasks().size());
    }

    @Test
    void validateTask() {
        Task task = new Task();
        task.setName("Test Task");
        task.setDescription("This is a test task");
        task.setStartTime(LocalDateTime.now());
        task.setDuration(Duration.ofHours(1));
        assertDoesNotThrow(() -> taskManager.validateTask(task));
    }
}

