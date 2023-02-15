package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();

    /* в общем залез в код который выложил наставник после вебинара и предположил что такую реализацию ты имел ввиду?
    * плюс в том же коде добавление задач в историю так же только в методах по поиску задач по ID, но я попробовал
    * реализовать то что ты меня просил. Теперь все ли верно?*/

    private int newId = 0;

    public List<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public ArrayList<Task> getTasks() {
        for (Task value : tasks.values()) { // вот тут
            inMemoryHistoryManager.add(value);
        }
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        for (Task value : epics.values()) { // тут
            inMemoryHistoryManager.add(value);
        }
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<Subtask> getSubtasks() {
        for (Task value : subtasks.values()) { // и вот тут. Это имелось ввиду?
            inMemoryHistoryManager.add(value);
        }
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Integer id : subtasks.keySet()) {
            deleteSubtaskById(id);
        }
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = tasks.get(id);
        inMemoryHistoryManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = epics.get(id);
        inMemoryHistoryManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = subtasks.get(id);
        inMemoryHistoryManager.add(subtask);
        return subtask;
    }

    @Override
    public void addTask(Task task) {
        Integer id = ++newId;
        task.setId(id);
        tasks.put(id, task);
    }

    @Override
    public void addEpic(Epic epic) {
        Integer id = ++newId;
        epic.setId(id);
        epics.put(id, epic);
    }

    @Override
    public void addSubtask(Subtask subtask, Epic epic) {
        Integer id = ++newId;
        subtask.setId(id);
        subtask.setEpicId(epic.getId());
        ArrayList<Integer> subtaskID = epic.listId();
        subtaskID.add(subtask.getId());
        epic.setSubtasksId(subtaskID);
        subtasks.put(id, subtask);
        changeEpicStatus(epic);
    }

    @Override
    public void updateTask(Task newTask) {
        tasks.put(newTask.getId(), newTask);
    }

    @Override
    public void updateEpic(Epic newEpic) {
        epics.put(newEpic.getId(), newEpic);
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        subtasks.put(newSubtask.getId(), newSubtask);
        changeEpicStatus(epics.get(newSubtask.getEpicId()));
    }

    @Override
    public void deleteTaskById(Integer id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        for (int i = 0; i < epics.get(id).getSubtasksId().size(); i++) {
            subtasks.remove(epics.get(id).getSubtasksId().get(i));
        }
        epics.remove(id);
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        int index = epics.get(subtasks.get(id).getEpicId()).getSubtasksId().indexOf(id);
        epics.get(subtasks.get(id).getEpicId()).getSubtasksId().remove(index);
        int epicId = subtasks.get(id).getEpicId();
        subtasks.remove(id);
        changeEpicStatus(epics.get(epicId));
    }

    @Override
    public List<Subtask> getListSubtaskByEpic(Epic epic) {
        List<Subtask> epicSubtasks = new ArrayList<>();
        ArrayList<Integer> listSubtask = epic.listId();
        for (Integer list : listSubtask) {
            epicSubtasks.add(subtasks.get(list));
        }
        return epicSubtasks;
    }

    @Override
    public void changeEpicStatus(Epic epic) {
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
