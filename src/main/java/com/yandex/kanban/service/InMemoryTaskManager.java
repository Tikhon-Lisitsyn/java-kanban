package main.java.com.yandex.kanban.service;

import main.java.com.yandex.kanban.model.Epic;
import main.java.com.yandex.kanban.model.Subtask;
import main.java.com.yandex.kanban.model.Task;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private static int taskCounter = 1;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private final Set<Task> prioritizedTasks;
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        this.epics = new HashMap<>();
        this.prioritizedTasks = new TreeSet<>();
    }

    @Override
    public Task addTask(Task task) {
        validateTask(task);
        if (task != null) {
            if (isTaskOverlapping(task)) {
                throw new IllegalArgumentException("The task overlaps with an existing task.");
            }
            int id = taskCounter++;
            task.setId(id);
            tasks.put(id, task);
            System.out.println("Добавлена задача: ");
            return tasks.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Task getTaskById(int id) {
        System.out.println("Задача по данному ID: ");
        if (historyManager.getHistory().size() == 10) {
            historyManager.getHistory().removeFirst();
            historyManager.add(tasks.get(id));
        } else {
            historyManager.add(tasks.get(id));
        }
        return tasks.get(id);
    }

    @Override
    public List<Task> getAllTasks() {
        ArrayList<Task> allTasks = new ArrayList<>(tasks.values());
        System.out.println("Список всех задач: ");
        return allTasks;
    }

    @Override

    public void removeAllTasks() {
        tasks.clear();
        System.out.println("Все задачи удалены!");
    }

    @Override
    public void removeTaskById(int id) {
        if (tasks.containsKey(id)) {
            System.out.println("Удалена задача по ID: " + tasks.get(id));
            tasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Неверный ID задачи!");
        }
    }

    @Override
    public Task updateTask(Task newTask, int id) {
        validateTask(newTask);
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            tasks.put(id, newTask);
            newTask.setStatus(TaskStatus.NEW);
            System.out.println("Задача обновлена: ");
        } else {
            System.out.println("Неверный ID задачи!");
        }
        return tasks.get(id);
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        ArrayList<Subtask> allSubtasks = new ArrayList<>(subtasks.values());
        System.out.println("Список всех подзадач: " + allSubtasks);
        return allSubtasks;
    }

    @Override
    public void removeAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getIdOfSubtasks().clear();
        }
        System.out.println("Все подзадачи удалены!");

    }

    @Override
    public Subtask getSubtaskById(int id) {
        System.out.println("Подзадача по данному ID: ");
        if (historyManager.getHistory().size() == 10) {
            historyManager.getHistory().removeFirst();
            historyManager.add(subtasks.get(id));
        } else {
            historyManager.add(subtasks.get(id));
        }
        return subtasks.get(id);
    }

    @Override
    public Subtask addSubtask(Subtask subtask, int epicId) {
        if (epics.containsKey(epicId)) {
            if (subtask != null) {
                int id = taskCounter++;
                subtask.setId(id);
                subtasks.put(id, subtask);
                epics.get(epicId).getIdOfSubtasks().add(id);
                calculateEpicStatus(epics.get(epicId));
                System.out.println("Добавлена подзадача: " + subtask);
                return subtasks.get(id);
            } else {
                return null;
            }
        } else {
            System.out.println("Нет эпика с таким ID!");
        }
        return null;
    }

    @Override
    public Subtask updateSubtask(Subtask newSubtask, int id) {
        if (subtasks.containsKey(id)) {
            subtasks.remove(id);
            subtasks.put(id, newSubtask);
            newSubtask.setStatus(TaskStatus.NEW);
            System.out.println("Подзадача обновлена: ");
        } else {
            System.out.println("Неверный ID подзадачи!");
        }
        return subtasks.get(id);
    }

    @Override
    public void removeSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            System.out.println("Удалена подзадача по ID: " + subtasks.get(id));
            subtasks.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Неверный ID подзадачи!");
        }
    }

    @Override
    public List<Epic> getAllEpics() {
        ArrayList<Epic> allEpics = new ArrayList<>(epics.values());
        System.out.println("Список всех эпиков: ");
        return allEpics;
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic : epics.values()) {
            epic.getIdOfSubtasks().clear();
        }
        epics.clear();
        System.out.println("Все эпики удалены!");
    }

    @Override
    public Epic getEpicById(int id) {
        System.out.println("Эпик по данному ID: ");
        if (historyManager.getHistory().size() == 10) {
            historyManager.getHistory().removeFirst();
            historyManager.add(epics.get(id));
        } else {
            historyManager.add(epics.get(id));
        }
        return epics.get(id);
    }

    @Override
    public Epic addEpic(Epic epic) {
        int id = taskCounter++;
        epic.setId(id);
        epics.put(id, epic);
        System.out.println("Добавлен эпик: " + epic);
        return epics.get(id);
    }

    @Override
    public Epic updateEpic(Epic newEpic, int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
            epics.put(id, newEpic);
            newEpic.setStatus(TaskStatus.NEW);
            System.out.println("Эпик обновлен: ");
        } else {
            System.out.println("Неверный ID эпика!");
        }
        return epics.get(id);
    }

    @Override
    public void removeEpicById(int id) {
        if (epics.containsKey(id)) {
            System.out.println("Удален эпик по ID: " + epics.get(id));
            epics.remove(id);
            historyManager.remove(id);
        } else {
            System.out.println("Неверный ID эпика!");
        }
    }

    @Override
    public List<Integer> subtasksOfEpic(int id) {
        return epics.get(id).getIdOfSubtasks();
    }

    @Override
    public TaskStatus getTaskStatus(Task task) {
        return task.getStatus();
    }

    @Override
    public int getSubid(Subtask subtask) {
        if (subtasks.containsValue(subtask)) {
            for (Subtask subtask1 : subtasks.values()) {
                if (subtask1.equals(subtask)) {

                }
            }
        } else {
            System.out.println("Такой подзадачи нет");
        }
        return 1;
    }

    @Override
    public void calculateEpicStatus(Epic epic) {
        boolean subtasksNew = true;
        boolean subtasksDone = true;
        for (int subid : epic.getIdOfSubtasks()) {
            final Subtask subtask = subtasks.get(subid);
            if (subtask.getStatus() != TaskStatus.NEW) {
                subtasksNew = false;
                break;
            }
            if (subtask.getStatus() != TaskStatus.DONE) {
                subtasksDone = false;
                break;
            }
        }
        if (subtasksNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (subtasksDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public boolean isOverlapping(Task task1,Task task2) {
        if (task1.getStartTime() == null || task1.getDuration() == null || task2.getStartTime() == null || task2.getDuration() == null) {
            return false;
        }
        LocalDateTime thisEndTime = task1.getStartTime().plus(task1.getDuration());
        LocalDateTime otherEndTime = task2.getStartTime().plus(task2.getDuration());
        return task1.getStartTime().isBefore(otherEndTime) && task2.getStartTime().isBefore(thisEndTime);
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        prioritizedTasks.clear();
        prioritizedTasks.addAll(tasks.values());
        prioritizedTasks.addAll(epics.values());
        prioritizedTasks.addAll(subtasks.values());
        return new TreeSet<>(prioritizedTasks);
    }

    public boolean areTasksOverlapping(int id1, int id2) {
        Task task1 = tasks.get(id1);
        Task task2 = tasks.get(id2);
        if (task1 == null || task2 == null) {
            throw new IllegalArgumentException("Неверные id задач.");
        }
        return isOverlapping(task1,task2);
    }

    @Override
    public void validateTask(Task task) {
        if (task.getName()==null || task.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("У задачи нет имени.");
        }
        if (task.getDescription()==null || task.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("У задачи нет описания.");
        }
        if (task.getStartTime()==null || task.getDuration()==null) {
            throw new IllegalArgumentException("Начало или длительность задачи не может быть null");
        }
    }

    public boolean isTaskOverlapping(Task task) {
        return tasks.values().stream().anyMatch(existingTask -> isOverlapping(existingTask,task));
    }
}
