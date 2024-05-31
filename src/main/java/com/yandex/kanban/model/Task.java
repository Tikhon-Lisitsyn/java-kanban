package main.java.com.yandex.kanban.model;

import main.java.com.yandex.kanban.service.TaskStatus;

import java.util.Objects;

public class Task {
    private String name;
    private String description;
    private int id;
    private TaskStatus status;
    private TaskStatus epicStatus;

    public Task() {
        status = TaskStatus.NEW;
    }

    @Override
    public String toString() {
        return "main.java.com.yandex.kanban.model.Task{" + "name='" + name + "' description= '" + description + "' id= '" + id + "' status= '" + status + "'}";
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
}
