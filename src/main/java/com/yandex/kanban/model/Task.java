package main.java.com.yandex.kanban.model;

import main.java.com.yandex.kanban.service.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task>{
    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private TaskStatus epicStatus;
    private Duration duration;
    private LocalDateTime startTime;

    public Task() {
        status = TaskStatus.NEW;
    }

    @Override
    public String toString() {
        return "main.java.com.yandex.kanban.model.Task{" + "name='" + name + "' description= '" + description
                + "' id= '" + id + "' status= '" + status + "'}";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setDuration(Duration duration) {
        this.duration=duration;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime=startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Task o) {
        if (this.startTime == null && o.startTime == null) {
            return 0;
        } else if (this.startTime == null) {
            return 1;
        } else if (o.startTime == null) {
            return -1;
        } else {
            return this.startTime.compareTo(o.startTime);
        }
    }
}