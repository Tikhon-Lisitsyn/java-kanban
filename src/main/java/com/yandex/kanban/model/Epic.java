package main.java.com.yandex.kanban.model;

import main.java.com.yandex.kanban.service.InMemoryTaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> idOfSubtasks;
    private LocalDateTime endTime;

    public Epic() {
        idOfSubtasks = new ArrayList<>();
    }

    public ArrayList<Integer> getIdOfSubtasks() {
        return idOfSubtasks;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Duration getDuration(int id) {
        return Duration.between(getStartTime(),getEndTime());
    }

    @Override
    public String toString() {
        return "main.java.com.yandex.kanban.model.Epic{" + "name='" + getName() + "' description= '" + getDescription()
                + "' id= '" + getId() + "' status= '" + getStatus() + "'" + "subtasksOfEpic}";
    }
}