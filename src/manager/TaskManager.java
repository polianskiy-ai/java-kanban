package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<Subtask> getSubtasks();

    void deleteAllTasks();

    void deleteAllEpics();

    void deleteAllSubtasks();

    Task getTaskById(Integer id);

    Epic getEpicById(Integer id);

    Subtask getSubtaskById(Integer id);

    void addTask(Task task);

    void addEpic(Epic epic);

    void addSubtask(Subtask subtask, Epic epic);

    void updateTask(Task newTask);

    void updateEpic(Epic newEpic);

    void updateSubtask(Subtask newSubtask);

    void deleteTaskById(Integer id);

    void deleteEpicById(Integer id);

    void deleteSubtaskById(Integer id);

    List<Subtask> getListSubtaskByEpic(Epic epic);

    void changeEpicStatus(Epic epic);

    List<Task> getHistory();
}