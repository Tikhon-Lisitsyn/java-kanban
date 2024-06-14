package main.java.com.yandex.kanban.service;

import main.java.com.yandex.kanban.model.Epic;
import main.java.com.yandex.kanban.model.Subtask;
import main.java.com.yandex.kanban.model.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private final File newFile;

    public FileBackedTaskManager(File newFile) {
        if (newFile == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        this.newFile = newFile;
    }

    public void save() {
        try (FileWriter fileWriter = new FileWriter(newFile)) {

            fileWriter.write("id,type,name,status,description,epic\n");

            List<Task> tasks = getAllTasks();
            for (Task task : tasks) {
                fileWriter.write(taskToCsv(task));
            }

            List<Subtask> subtasks = getAllSubtasks();
            for (Subtask subtask : subtasks) {
                fileWriter.write(subtaskToCsv(subtask));
            }

            List<Epic> epics = getAllEpics();
            for (Epic epic : epics) {
                fileWriter.write(epicToCsv(epic));
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private String taskToCsv(Task task) {
        return String.format("%d,TASK,%s,%s,%s,%n",
                task.getId(),
                task.getName(),
                task.getStatus(),
                task.getDescription());
    }

    private String epicToCsv(Epic epic) {
        return String.format("%d,EPIC,%s,%s,%s,%n",
                epic.getId(),
                epic.getName(),
                epic.getStatus(),
                epic.getDescription());
    }

    private String subtaskToCsv(Subtask subtask) {
        return String.format("%d,SUBTASK,%s,%s,%s,%d%n",
                subtask.getId(),
                subtask.getName(),
                subtask.getStatus(),
                subtask.getDescription(),
                subtask.getEpicId());
    }

    static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        FileBackedTaskManager taskManager = new FileBackedTaskManager(file);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                String[] fields = line.split(",");
                String type = fields[1];
                int id = Integer.parseInt(fields[0]);
                String name = fields[2];
                TaskStatus status = TaskStatus.valueOf(fields[3]);
                String description = fields[4];

                switch (type) {
                    case "TASK":
                        Task task = new Task();
                        task.setName(name);
                        task.setDescription(description);
                        task.setId(id);
                        task.setStatus(status);
                        taskManager.addTask(task);
                        break;
                    case "EPIC":
                        Epic epic = new Epic();
                        epic.setName(name);
                        epic.setDescription(description);
                        epic.setId(id);
                        epic.setStatus(status);
                        taskManager.addEpic(epic);
                        break;
                    case "SUBTASK":
                        int epicId = Integer.parseInt(fields[5]);
                        Subtask subtask = new Subtask();
                        subtask.setName(name);
                        subtask.setDescription(description);
                        subtask.setId(id);
                        subtask.setStatus(status);
                        subtask.setEpicId(epicId);
                        taskManager.addSubtask(subtask, epicId);
                        break;
                    default:
                        System.out.println("Неизвестный тип задачи: " + type);
                        break;


                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при вводе-выводе данных");
        }

        return taskManager;
    }


    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public List<Task> getAllTasks() {
        List<Task> allTasks = super.getAllTasks();
        save();
        return allTasks;
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        List<Subtask> allSubtask = super.getAllSubtasks();
        save();
        return allSubtask;
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = super.getSubtaskById(id);
        save();
        return subtask;
    }

    @Override
    public List<Epic> getAllEpics() {
        List<Epic> allEpics = super.getAllEpics();
        save();
        return allEpics;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public List<Integer> subtasksOfEpic(int id) {
        List<Integer> subtasksOfEpic = super.subtasksOfEpic(id);
        save();
        return subtasksOfEpic;
    }
}
