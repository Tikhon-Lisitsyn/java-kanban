package main.java.com.yandex.kanban.service;

import main.java.com.yandex.kanban.model.Epic;
import main.java.com.yandex.kanban.model.Subtask;
import main.java.com.yandex.kanban.model.Task;

import java.util.List;

public interface TaskManager {

    public Task addTask(Task task);

    public Task getTaskById(int id);

    public List<Task> getAllTasks();

    public void removeAllTasks();

    public void removeTaskById(int id);

    public Task updateTask(Task newTask, int id);

    public List<Subtask> getAllSubtasks();

    public void removeAllSubtasks();

    public Subtask getSubtaskById(int id);

    public Subtask addSubtask(Subtask subtask, int epicId);

    public Subtask updateSubtask(Subtask newSubtask, int id);

    public void removeSubtaskById(int id);

    public List<Epic> getAllEpics();

    public void removeAllEpics();

    public Epic getEpicById(int id);

    public Epic addEpic(Epic epic);

    public Epic updateEpic(Epic newEpic, int id);

    public void removeEpicById(int id);

    public List<Integer> subtasksOfEpic(int id);

    public TaskStatus getTaskStatus(Task task);

    public int getSubid(Subtask subtask);

    public void calculateEpicStatus(Epic epic);
}