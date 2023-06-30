package service;

import com.google.gson.Gson;
import model.Epic;
import model.Subtask;
import model.Task;
import model.TaskStatus;

import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    Gson gson = new Gson();

    protected Map<Integer, Task> tasks;
    protected Map<Integer, Epic> epics;
    protected Map<Integer, Subtask> subtasks;
    protected final HistoryManager historyManager;
    private final Comparator<Task> comparator = (o1, o2) -> {
        if (o1.equals(o2)) {
            return 0;
        } else if (o1.getStartTime() == null) {
            return 1;
        } else if (o2.getStartTime() == null) {
            return -1;
        } else return o1.getStartTime().compareTo(o2.getStartTime());
    };

    protected Set<Task> priorityTasks = new TreeSet<>(comparator);

    protected int newId = 0;

    public InMemoryTaskManager(HistoryManager historyManager) {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        this.historyManager = historyManager;

    }

    protected void setTasksById(HashMap<Integer, Task> tasks) {
        this.tasks = tasks;
    }

    protected void setEpicsById(HashMap<Integer, Epic> epics) {
        this.epics = epics;
    }

    protected void setSubtasksById(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }


    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void deleteAllTasks() {
        for (Integer id : tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void deleteAllEpics() {

        for (Integer id : epics.keySet()) {
            historyManager.remove(id);
        }
        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
        }
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.deleteSubtasks();
            changeEpicStatus(epic);
        }
        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
    }

    @Override
    public Task getTaskById(Integer id) {
        Task task = tasks.get(id);
        historyManager.add(task);
        return task;
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        return epic;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        return subtask;
    }

    @Override
    public void addTask(Task task) {
        if (task == null) {
            throw new NullPointerException("Task can't be null");
        }
        int id = ++newId;
        task.setId(id);
        tasks.put(id, task);
        addTaskToPrioritizedTasks(task);
    }

    @Override
    public void addEpic(Epic epic) {
        if (epic == null) {
            throw new NullPointerException("Epic can't be null");
        }
        int id = ++newId;
        epic.setId(id);
        epics.put(id, epic);
    }

    @Override
    public void addSubtask(Subtask subtask) {
        if (subtask == null) {
            throw new NullPointerException("Subtask can't be null");
        } else {
            int id = ++newId;
            subtask.setId(id);
            subtasks.put(id, subtask);
            addTaskToPrioritizedTasks(subtask);
            Epic epic = epics.get(subtask.getEpicId());
            epic.addSubtasksId(id);
            changeEpicStatus(epic);
            changeEpicTime(epic);
        }
    }

    @Override
    public void updateTask(int id, Task newTask) {
        tasks.put(id, newTask);
        addTaskToPrioritizedTasks(newTask);
    }

    @Override
    public void updateEpic(int id, Epic newEpic) {
        epics.put(id, newEpic);
    }

    @Override
    public void updateSubtask(int id, Subtask newSubtask) {
        subtasks.put(id, newSubtask);
        changeEpicStatus(epics.get(newSubtask.getEpicId()));
        changeEpicTime(epics.get(newSubtask.getEpicId()));
        addTaskToPrioritizedTasks(newSubtask);
    }

    @Override
    public void deleteTaskById(Integer id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteEpicById(Integer id) {
        for (Integer subtaskId : epics.get(id).getSubtasksId()) {
            subtasks.remove(subtaskId);
            historyManager.remove(subtaskId);
        }
        epics.remove(id);
        historyManager.remove(id);
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        int epicId = subtasks.remove(id).getEpicId();
        Epic epic = epics.get(epicId);
        epic.getSubtasksId().remove(id);
        historyManager.remove(id);
        changeEpicStatus(epic);
        changeEpicTime(epic);
    }

    @Override
    public List<Subtask> getListSubtaskByEpic(int id) {
        if (epics.containsKey(id)) {
            List<Subtask> epicSubtasks = new ArrayList<>();
            Epic listSubtask = epics.get(id);
            for (Integer list : listSubtask.getSubtasksId()) {
                epicSubtasks.add(subtasks.get(list));
            }
            return epicSubtasks;
        } else {
            return null;
        }
    }


    private void changeEpicStatus(Epic epic) {
        int doneStatus = 0;
        int newStatus = 0;
        for (Integer id : epic.getSubtasksId()) {
            TaskStatus status = subtasks.get(id).getStatus();
            if (status == TaskStatus.DONE) {
                doneStatus++;
            } else if (status == TaskStatus.NEW) {
                newStatus++;
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
                return;
            }
        }
        if (doneStatus == epic.getSubtasksId().size()) {
            epic.setStatus(TaskStatus.DONE);
        } else if (newStatus == epic.getSubtasksId().size()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    private void changeEpicTime(Epic epic) {
        List<Subtask> subtasks = getListSubtaskByEpic(epic.getId());
        if (!subtasks.isEmpty()) {
            LocalDateTime startTime = subtasks.get(0).getStartTime();
            if (startTime != null) {
                int duration = 0;
                for (Subtask subtask : subtasks) {
                    if (subtask.getStartTime() != null) {
                        duration += subtask.getDuration();
                        if (subtask.getStartTime().isBefore(startTime)) {
                            startTime = subtask.getStartTime();
                        }
                    }
                }
                epic.setStartTime(startTime);
                epic.setDuration(duration);
                epic.setEndTime(startTime.plusMinutes(duration));
            }
        }
    }

    private void addTaskToPrioritizedTasks(Task task){
        if (validateTasks(task)) {
            priorityTasks.add(task);
        } else throw new ManagerSaveException("Задача пересекается с другой задачей");
    }

    public List<Task> getPrioritizedTasks(){
        return new ArrayList<>(priorityTasks);
    }

    private boolean validateTasks(Task task){
        List<Task> list = getPrioritizedTasks();
        boolean isNotIntersection = true;
        for (Task taskItem : list) {
            if (!task.equals(taskItem)) {
                if (task.getStartTime() != null && taskItem.getEndTime() != null) {
                    if (task.getEndTime().isBefore(taskItem.getStartTime())
                            || task.getEndTime().isBefore(taskItem.getStartTime())) {
                        isNotIntersection = true;
                    } else if (task.getStartTime().isAfter(taskItem.getEndTime())
                            || task.getEndTime().isAfter(taskItem.getEndTime())) {
                        isNotIntersection = true;
                    } else {
                        isNotIntersection = false;
                        break;
                    }
                }
            }
        }   return isNotIntersection;
    }
}
