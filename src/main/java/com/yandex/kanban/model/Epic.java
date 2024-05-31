package main.java.com.yandex.kanban.model;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> idOfSubtasks;

    public Epic() {
        idOfSubtasks = new ArrayList<>();
    }

    public ArrayList<Integer> getIdOfSubtasks() {
        return idOfSubtasks;
    }

    @Override
    public String toString() {
        return "main.java.com.yandex.kanban.model.Epic{" + "name='" + getName() + "' description= '" + getDescription()
                + "' id= '" + getId() + "' status= '" + getStatus() + "'" + "subtasksOfEpic}";
    }
}