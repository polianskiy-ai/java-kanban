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

    public void getAllTasks(){
        for (Task list : tasks.values()){
            System.out.println(list);
        }
        for (Epic list : epics.values()){
            System.out.println(list);
        }
        for (Subtask list : subtasks.values()){
            System.out.println(list);
        }
    }

    public void deleteAllTasks(){
        tasks.clear();
        epics.clear();
        subtasks.clear();
        System.out.println("Все задачи удалены");
    }

    public void getTaskById(Integer id){
        Task task = tasks.get(id);
        System.out.println(task);
    }
    public void getEpicById(Integer id){
        Epic epic = epics.get(id);
        System.out.println(epic);
    }
    public void getSubtaskById(Integer id){
        Subtask subtask = subtasks.get(id);
        System.out.println(subtask);
    }

    public void addTask(Task task){
        Integer id = ++newId;
        task.setId(id);
        tasks.put(id, task);
        System.out.println("Новая задача создана, ее идентификатор - " + id);
    }
    public void addEpic(Epic epic){
        Integer id = ++newId;
        epic.setId(id);
        epics.put(id, epic);
        System.out.println("Новый эпик создан, его идентификатор - " + id);
    }
    public void addSubtask(Subtask subtask, Epic epic){
        Integer id = ++newId;
        subtask.setId(id);
        subtask.setEpicId(epic.getId());
        ArrayList<Integer> subtaskID;
        if (epic.getSubtaskId() != null){
            subtaskID = epic.getSubtaskId();
        } else {
            subtaskID = new ArrayList<>();
        }
        subtaskID.add(subtask.getId());
        epic.setSubtaskId(subtaskID);
        subtasks.put(id, subtask);
        changeEpicStatus(epic);
        System.out.println("Новая подзадача создан, ее идентификатор - " + id);
    }

    public void updateTask(Integer id, String newNameTitle, String newDescription){
        Task task = tasks.get(id);
        if (task != null){
            tasks.get(id).setNameTitle(newNameTitle);
            tasks.get(id).setDescription(newDescription);
        } else {
            System.out.println("Нет такого идентификатора");
        }
    }
    public void updateEpic(Integer id, String newNameTitle, String newDescription){
        Epic epic = epics.get(id);
        if (epic != null){
            epics.get(id).setNameTitle(newNameTitle);
            epics.get(id).setDescription(newDescription);
        } else {
            System.out.println("Нет такого идентификатора");
        }
    }
    public void updateSubtask(Integer id, String newNameTitle, String newDescription){
        Subtask subtask = subtasks.get(id);
        if (subtask != null){
            subtasks.get(id).setNameTitle(newNameTitle);
            subtasks.get(id).setDescription(newDescription);
        } else {
            System.out.println("Нет такого идентификатора");
        }
    }

    public void deleteById(Integer id){
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            epics.remove(id);
        } else if (subtasks.containsKey(id)) {
            subtasks.remove(id);
        }
        System.out.println("Задача с идентификатором " + id + " удалена");
    }

    public void getListSubtaskByEpic(Epic epic){
        ArrayList<Integer> listSubtask = epic.listId();
        for (Integer list : listSubtask){
            System.out.println(subtasks.get(list));
        }
    }

    public void changeTaskStatus(Task task, TaskStatus status){
        task.setStatus(status);
    }

    public void changeEpicStatus(Epic epic){
        int doneStatus = 0;
        int newStatus = 0;
        for (Integer id : epic.getSubtaskId()){
            if (subtasks.get(id).getStatus() == TaskStatus.DONE){
                doneStatus++;
            } else if (subtasks.get(id).getStatus() == TaskStatus.NEW){
                newStatus++;
            }
        }
        if (doneStatus == epic.getSubtaskId().size()){
            epic.setStatus(TaskStatus.DONE);
        } else if (newStatus == epic.getSubtaskId().size()){
            epic.setStatus(TaskStatus.NEW);
        } else epic.setStatus(TaskStatus.IN_PROGRESS);
        epics.put(epic.getId(), epic);
    }
}