package main.java.com.yandex.kanban.model;

public class Subtask extends Task {
    private int epicId;
    public Subtask() {

    }

    @Override
    public String toString() {
        return "main.java.com.yandex.kanban.model.Subtask{" + "name='" +getName() + "' description= '"+getDescription()
                 + "' id= '" + getId() + "' status= '" + getStatus() + "' epicId='" + epicId + "'" + "}";
    }
}