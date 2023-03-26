package model;

public class Task {
    private String nameTitle;
    private String description;
    private int id;
    private TaskStatus status = TaskStatus.NEW;
    private TaskType type;

    public Task(TaskType type, String nameTitle, String description, TaskStatus status) {
        this.type = type;
        this.nameTitle = nameTitle;
        this.description = description;
        this.status = status;
    }

    public Task(TaskType type,String nameTitle, String description) {
        this.type = type;
        this.nameTitle = nameTitle;
        this.description = description;
    }

    public Task(int id, String nameTitle, String description) {
        this.id = id;
        this.nameTitle = nameTitle;
        this.description = description;
    }

    public TaskType getType() {
        return type;
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "nameTitle = " + nameTitle +
                ", description = " + description +
                ", id = " + id +
                ", status = " + status +
                '}' + "\n";
    }
}
