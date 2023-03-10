package manager;
import tasks.Task;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;



public class InMemoryHistoryManager implements HistoryManager {
    public Node head;
    public Node tail;
    public int size;
    private Map<Integer, Node> history = new HashMap<>();

    @Override
    public void add(Task task) {
        Node tailNode = tail;
        Node newNode = new Node(task);
        tail = newNode;
        if (tailNode == null) {
            head = newNode;
        } else {
            tailNode.next = newNode;
        }
        size++;
        history.put(task.getId(), tail);
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
        List<Task> tasks = new ArrayList<>();
        Node newNode = head;
        while (newNode != null) {
            tasks.add(newNode.task);
            newNode = newNode.next;
        }
        return tasks;
    }


    class Node {
        Task task;
        Node prev;
        Node next;

        public Node(Task task) {
            this.task = task;
        }
    }
}
