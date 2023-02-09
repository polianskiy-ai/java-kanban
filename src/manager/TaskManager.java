package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int newId = 0;

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
    }

    public Task getTaskById(Integer id) {
        Task task = tasks.get(id);
        return task;
    }

    public Epic getEpicById(Integer id) {
        Epic epic = epics.get(id);
        return epic;
    }

    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = subtasks.get(id);
        return subtask;
    }

    public void addTask(Task task) {
        Integer id = ++newId;
        task.setId(id);
        tasks.put(id, task);
    }

    public void addEpic(Epic epic) {
        Integer id = ++newId;
        epic.setId(id);
        epics.put(id, epic);
    }

    public void addSubtask(Subtask subtask, Epic epic) {
        Integer id = ++newId;
        subtask.setId(id);
        subtask.setEpicId(epic.getId());
        ArrayList<Integer> subtaskID;
        subtaskID = epic.listId();
        subtaskID.add(subtask.getId());
        epic.setSubtasksId(subtaskID);
        subtasks.put(id, subtask);
        changeEpicStatus(epic);
    }

    public void updateTask(Integer id, Task newTask) {
        newTask.setId(id);
        tasks.put(id, newTask);
    }

    public void updateEpic(Integer id, Epic newEpic) {
        newEpic.setId(id);
        epics.put(id, newEpic);
    }

    public void updateSubtask(Integer id, Subtask newSubtask) {
        newSubtask.setId(id);
        subtasks.put(id, newSubtask);
    }

    public void deleteTaskById(Integer id) {
        tasks.remove(id);
    }

    public void deleteEpicById(Integer id) {
        for (int i = 0; i < epics.get(id).getSubtasksId().size(); i++) {
            subtasks.remove(epics.get(id).getSubtasksId().get(i));
        }
        epics.remove(id);
       // changeEpicStatus(subtasks.get(id).getEpicId());
    }

    public void deleteSubtaskById(Integer id) {
        int index = epics.get(subtasks.get(id).getEpicId()).getSubtasksId().indexOf(id);
        epics.get(subtasks.get(id).getEpicId()).getSubtasksId().remove(index);
        subtasks.remove(id);
    }


    public String getListSubtaskByEpic(Epic epic) {
        String i = "";
        ArrayList<Integer> listSubtask = epic.listId();
        for (Integer list : listSubtask) {
            i += subtasks.get(list);
        }
        return i;
    }


    private void changeEpicStatus(Epic epic) {
        int doneStatus = 0;
        int newStatus = 0;
        for (Integer id : epic.getSubtasksId()) {
            if (subtasks.get(id).getStatus() == TaskStatus.DONE) {
                doneStatus++;
            } else if (subtasks.get(id).getStatus() == TaskStatus.NEW) {
                newStatus++;
            }
        }
        if (doneStatus == epic.getSubtasksId().size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (newStatus == epic.getSubtasksId().size()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
        epics.put(epic.getId(), epic);
    }
}