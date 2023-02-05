package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int newId = 1;

    public void getListTask() {
        for (Integer id : tasks.keySet()) {
            System.out.println(id);
            for (Task task : tasks.values()) {
                System.out.println(task);
            }
        }
    }

    public void getListEpic() {
        for (Integer id : epics.keySet()) {
            System.out.println(id);
            for (Epic epic : epics.values()) {
                System.out.println(epic);
            }
        }
    }

    public void getListSubtask() {
        for (Integer id : subtasks.keySet()) {
            System.out.println(id);
            for (Subtask subtask : subtasks.values()) {
                System.out.println(subtask);
            }
        }
    }

    public void deleteTask() {
        tasks.clear();
    }

    public void deleteEpic() {
        epics.clear();
    }

    public void deleteSubtask() {
        subtasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public Task creatTask (Task task){
        task.setId(newId++);
        tasks.put(task.getId(), task);
        return task;
    }
    public Epic creatEpic (Epic epic){
        epic.setId(newId++);
        epics.put(epic.getId(), epic);
        return epic;
    }
    public Epic creatSubtask (Subtask subtask){
        subtask.setId(newId++);
        subtasks.put(subtask.getId(), subtask);
        return subtask;
    }

    public void updateTask (int Id, Task updateTask){
        Task id = tasks.get(Id);
        if (id == null){
            System.out.println("Такой задачи нет");
        }
        tasks.put(Id, updateTask);
    }
    public void updateEpic (int Id, Epic updateEpic){
        Task id = epics.get(Id);
        if (id == null){
            System.out.println("Такой задачи нет");
        }
        tasks.put(Id, updateEpic);
    }

    public void updateSubtask (int Id, Subtask updateSubtask){
        Task id = tasks.get(Id);
        if (id == null){
            System.out.println("Такой задачи нет");
        }
        tasks.put(Id, updateSubtask);
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }
    public void deleteEpicById(int id) {
        epics.remove(id);
    }
    public void deleteSubtaskById(int id) {
        subtasks.remove(id);
    }

    public void printSubtaskByEpic (Epic epic){
        for (Integer id : epics.getId()){

        }
    }



}
