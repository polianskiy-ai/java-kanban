package service;
import model.Task;

import java.util.*;


public class InMemoryHistoryManager implements HistoryManager {
    private Node head;
    private Node tail;
    private int size;
    private Map<Integer, Node> nodes = new HashMap<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            removeNode(nodes.get(task.getId()));
            linkLast(task);
        }
    }

    @Override
    public void remove(int id) {
        removeNode(nodes.remove(id));
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Task task) {
            Node node = new Node(task);
            if (size == 0) {
                head = node;
            } else {
                tail.next = node;
                node.prev = tail;
            }
            tail = node;
            size++;
            nodes.put(task.getId(), node);
    }

    private void removeNode(Node node) {
        if (node != null) {
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
        }
    }

    private List<Task> getTasks() {
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
