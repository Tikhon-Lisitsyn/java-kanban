package main.java.com.yandex.kanban.service;

import java.io.IOException;

public class ManagerSaveException extends IOException {
    public ManagerSaveException(final String message) {
        super(message);
    }
}
