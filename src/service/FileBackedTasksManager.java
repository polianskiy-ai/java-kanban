package service;

import model.*;

import java.io.*;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private final String fileName;

    public FileBackedTasksManager(String fileName) throws IOException {
        this.fileName = fileName;
       load();
    }

    public void load() throws IOException {
        FileReader reader = new FileReader(this.fileName);
        BufferedReader br = new BufferedReader(reader);
        String line = "";

        while (br.ready()) {
            line =  line + br.readLine()+"\n";
        }
        br.close();

        String[] lines = line.split("\r?\n");
        for (int i = 1; i < lines.length - 2; i++) {
            Task task = fromString(lines[i]);
            if (task.getType() == TaskType.TASK) {
                addTask(task);
            } else if (task.getType() == TaskType.EPIC) {
                addEpic((Epic) task);
            } else if (task.getType() == TaskType.SUBTASK) {
                addSubtask((Subtask) task);
            }
        }

        for (String historyLine : lines[lines.length - 1].split("")) {
            String[] historyId = historyLine.split(",");
            for (String id : historyId) {
                if (tasks.containsKey(Integer.parseInt(id))) {
                    getTaskById(Integer.parseInt(id));
                } else if (epics.containsKey(Integer.parseInt(id))) {
                    getEpicById(Integer.parseInt(id));
                } else if (subtasks.containsKey(Integer.parseInt(id))) {
                    getSubtaskById(Integer.parseInt(id));
                }
            }
        }
    }


    public void save() {
        try {
            FileWriter writer = new FileWriter(fileName);
            String line = "id,type,name,status,description,epic \n";
            String history = "";
            for (Task task : getTasks()) {
                line = line + toString(task) + "\n";
            }
            for (Epic epic : getEpics()) {
                line = line + toString(epic) + "\n";
            }
            for (Subtask subtask : getSubtasks()) {
                line = line + toString(subtask) + "\n";
            }
            for (Task node : getHistory()) {
                history = history + node.getId() + ",";
            }
            line = line + "\n" + history;
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String toString(Task task) {
        int id = task.getId();
        TaskType type = task.getType();
        String name = task.getNameTitle();
        String description = task.getDescription();
        TaskStatus status = task.getStatus();
        if (task.getType() == TaskType.SUBTASK) {
            Subtask subtask = (Subtask) task;
            int epicId = subtask.getEpicId();
            return String.format("%d,%s,%s,%s,%s,%d", id, task.getType(), name, status, description, epicId);
        } else {
            return String.format("%d,%s,%s,%s,%s", id, task.getType(), name, status, description);
        }
    }

    public Task fromString(String value) {
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        TaskType type = TaskType.valueOf(values[1]);
        String name = values[2];
        TaskStatus status = TaskStatus.valueOf(values[3]);
        String description = values[4];
        if (type == TaskType.TASK) {
            return new Task(type, name, description, status);
        } else if (type == TaskType.EPIC) {
            return new Epic(type, name, description);
        } else {
            int epicId = Integer.parseInt(values[5]);
            return new Subtask(type, name, description, status, epicId);
        }
    }

    public static void main(String[] args) throws IOException {
        FileBackedTasksManager manager = new FileBackedTasksManager("src/resources/TaskHistory.csv");
        Task task1 = new Task(TaskType.TASK, "Задача 1", "Описание 1", TaskStatus.NEW);
        Task task2 = new Task(TaskType.TASK, "Задача 2", "Описание 2", TaskStatus.NEW);
        Epic epic1 = new Epic(TaskType.EPIC, "Эпик 1", "Описание эпика 1 с тремя подзадачами");
        Subtask subtask1 = new Subtask(TaskType.SUBTASK, "Подзадача 1", "Подзадача у эпика - 1", TaskStatus.DONE, 3);
        Subtask subtask2 = new Subtask(TaskType.SUBTASK, "Подзадача 2", "Подзадача у эпика - 1", TaskStatus.IN_PROGRESS, 3);
        Subtask subtask3 = new Subtask(TaskType.SUBTASK, "Подзадача 3", "Подзадача у эпика - 1", TaskStatus.IN_PROGRESS, 3);
        Epic epic2 = new Epic(TaskType.EPIC, "Эпик 2", "Описание эпика 2 без подзадач");

        manager.addTask(task1);
        manager.addTask(task2);
        manager.addEpic(epic1);
        manager.addSubtask(subtask1);
        manager.addSubtask(subtask2);
        manager.addSubtask(subtask3);
        manager.addEpic(epic2);
        System.out.println("История просмотров 1 - " + manager.getHistory());
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getEpicById(3);
        manager.getSubtaskById(4);
        manager.getSubtaskById(5);
        manager.getSubtaskById(6);
        manager.getEpicById(7);
        System.out.println("История просмотров 2 - " + manager.getHistory());
    }

/*
    @Override
    public List<Task> getTasks() {
        return super.getTasks();
    }

    @Override
    public List<Epic> getEpics() {
        return super.getEpics();
    }

    @Override
    public List<Subtask> getSubtasks() {
        return super.getSubtasks();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public Task getTaskById(Integer id) {
        return super.getTaskById(id);
    }

    @Override
    public Epic getEpicById(Integer id) {
        return super.getEpicById(id);
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        return super.getSubtaskById(id);
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task newTask) {
        super.updateTask(newTask);
        save();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        save();
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        super.updateSubtask(newSubtask);
        save();
    }

    @Override
    public void deleteTaskById(Integer id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public void deleteEpicById(Integer id) {
        super.deleteEpicById(id);
        save();
    }

    @Override
    public void deleteSubtaskById(Integer id) {
        super.deleteSubtaskById(id);
        save();
    }*/
}
