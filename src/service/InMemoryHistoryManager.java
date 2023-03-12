package service;
import model.Task;

import java.util.*;


public class InMemoryHistoryManager implements HistoryManager {
    private Node head;
    private Node tail;
    private int size;
    private Map<Integer, Node> history = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            Node node = new Node(task);
            Node existingNode = history.get(task.getId());
            if (existingNode != null) {
                Node prev = existingNode.prev;
                Node next = existingNode.next;
                if (prev != null) {
                    prev.next = next;
                } else {
                    head = next;
                }
                if (next != null) {
                    next.prev = prev;
                } else {
                    tail = prev;
                }
                size--;
            }
            if (size == 0) {
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
            size++;
            history.put(task.getId(), node);
        }
    }



    @Override
    public void remove(int id) {
        Node node = history.get(id);
        if (node == null) {
            return;
        }
        Node prev = node.prev;
        Node next = node.next;
        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
        size--;
        history.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> tasks = new LinkedList<>();
        Node newNode = head;
        while (newNode != null) {
            tasks.add(newNode.task);
            newNode = newNode.next;
        }
        return tasks;
    }


    private static class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task) {
            this.task = task;
        }
    }
}
