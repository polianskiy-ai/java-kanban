package service;

import model.*;

import java.io.*;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    private final String fileName;

    public FileBackedTasksManager(HistoryManager historyManager) throws IOException {
        super(historyManager);
        fileName = "src/resources/TaskHistory.csv";
        load();
    }

    public void load() throws IOException {
        FileReader reader = new FileReader(this.fileName);
        BufferedReader br = new BufferedReader(reader);
        String line = "";

        while (br.ready()) {
            line = line + br.readLine() + "\n";
        }
        br.close();

        String[] lines = line.split("\r?\n");
        for (int i = 1; i < lines.length - 2; i++) {
            Task task = fromString(lines[i]);
            if (task.getType() == TaskType.TASK) {
                super.addTask(task);
            } else if (task.getType() == TaskType.EPIC) {
                super.addEpic((Epic) task);
            } else if (task.getType() == TaskType.SUBTASK) {
                super.addSubtask((Subtask) task);
            }
        }


        for (String historyLine : lines[lines.length - 1].split("")) {
            if (historyLine != null) {
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
            } else {
                return;
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
            history = history + historyToString(historyManager);
            line = line + "\n" + history;
            writer.write(line);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String historyToString(HistoryManager manager) {
        String historyId = "";
        for (Task task : manager.getNodes()) {
            historyId += task.getId() + ",";
        }
        return historyId;
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
        FileBackedTasksManager manager = new FileBackedTasksManager(Manager.getDefaultHistory());
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpics());
        System.out.println(manager.getSubtasks());
        System.out.println("------");
        System.out.println(manager.getHistory());
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
        Task task = tasks.get(id);
        historyManager.add(task);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(Integer id) {
        Epic epic = epics.get(id);
        historyManager.add(epic);
        save();
        return epic;
    }

    @Override
    public Subtask getSubtaskById(Integer id) {
        Subtask subtask = subtasks.get(id);
        historyManager.add(subtask);
        save();
        return subtask;
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
    }
}
