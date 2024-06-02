package main.java.com.yandex.kanban.service;

import main.java.com.yandex.kanban.model.Task;
import main.java.com.yandex.kanban.service.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.LinkedHashMap;

public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node<Task>> tasks;
    private final LinkedList<Node<Task>> viewedTasks;

    private Node<Task> firstNode = null;
    private Node<Task> lastNode = null;

    private class Node<T> {
        private final T item;
        private Node<T> next;
        private Node<T> prev;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    public InMemoryHistoryManager() {
        viewedTasks = new LinkedList<>();
        tasks = new HashMap<>();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node<Task> node = firstNode;
        while (node != null) {
            history.add(node.item);
            node = node.next;
        }
        return history;
    }

    @Override
    public void add(Task task) {
        if (tasks.containsKey(task.getId())) {
            Node<Task> node = tasks.get(task.getId());
            removeNode(node);
        }
        Node<Task> newNode = new Node<>(task, null, lastNode);
        if (lastNode != null) {
            lastNode.next = newNode;
        } else {
            firstNode = newNode;
        }
        lastNode = newNode;
        tasks.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        if (tasks.containsKey(id)) {
            Node<Task> node = tasks.get(id);
            removeNode(node);
            tasks.remove(id);
        }
    }

    private void linkLast(Task task) {
        viewedTasks.addLast(new Node<>(task, firstNode, lastNode));
        firstNode = viewedTasks.getFirst();
        lastNode = viewedTasks.getLast();
    }

    private List<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        for (Node<Task> node : viewedTasks) {
            tasks.add(node.item);
        }
        return tasks;
    }

    private void removeNode(Node<Task> node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            firstNode = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            lastNode = node.prev;
        }
        tasks.remove(node.item.getId());
    }
}