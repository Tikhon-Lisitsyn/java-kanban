package main.java.com.yandex.kanban.model;

import main.java.com.yandex.kanban.service.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> idOfSubtasks;
    private LocalDateTime endTime;
    private final InMemoryTaskManager taskManager;

    public Epic() {
        idOfSubtasks = new ArrayList<>();
        taskManager = new InMemoryTaskManager();
    }

    public ArrayList<Integer> getIdOfSubtasks() {
        return idOfSubtasks;
    }

    public LocalDateTime getStartTime(int epicId) {
        LocalDateTime minStartTime=taskManager.getSubtaskById(taskManager.getEpicById(epicId).getIdOfSubtasks()
                .getFirst()).getStartTime();
        for (Integer id:idOfSubtasks) {
            LocalDateTime startTime=taskManager.getSubtaskById(id).getStartTime();
            if (startTime.isBefore(minStartTime)) {
                minStartTime=startTime;
            }
        }
        return minStartTime;
    }

    public LocalDateTime getEndTime(int epicId) {
        LocalDateTime maxEndTime=taskManager.getSubtaskById(taskManager.getEpicById(epicId).getIdOfSubtasks()
                .getFirst()).getStartTime();
        for (Integer id:idOfSubtasks) {
            LocalDateTime endTime=taskManager.getSubtaskById(id).getStartTime();
            if (endTime.isAfter(maxEndTime)){
                maxEndTime=endTime;
            }
        }
        return maxEndTime;
    }

    public Duration getDuration(int id) {
        return Duration.between(getStartTime(id),getEndTime(id));
    }

    @Override
    public String toString() {
        return "main.java.com.yandex.kanban.model.Epic{" + "name='" + getName() + "' description= '" + getDescription()
                + "' id= '" + getId() + "' status= '" + getStatus() + "'" + "subtasksOfEpic}";
    }
}