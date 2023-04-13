package model;

import java.time.LocalDateTime;

public class Task {
    private String nameTitle;
    private String description;
    private int id;
    private TaskStatus status = TaskStatus.NEW;
    private TaskType type;
    private int duration;
    private LocalDateTime startTime;
    private LocalDateTime getEndTime;

    public Task(TaskType type, String nameTitle, String description, TaskStatus status, int duration, LocalDateTime startTime) {
        this.type = type;
        this.nameTitle = nameTitle;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(TaskType type,String nameTitle, String description, int duration, LocalDateTime startTime) {
        this.type = type;
        this.nameTitle = nameTitle;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(TaskType type, String nameTitle, String description) {
        this.type = type;
        this.nameTitle = nameTitle;
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getGetEndTime() {
        return startTime.plusMinutes(duration);
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
                "nameTitle='" + nameTitle + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", type=" + type +
                ", duration=" + duration + " minutes" +
                ", startTime=" + startTime +
                '}' + "\n";
    }
}
