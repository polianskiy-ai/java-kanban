package model;

import java.time.LocalDateTime;

public class Subtask extends Task {
    private int epicId;

    public Subtask(TaskType type, String nameTitle, String description, TaskStatus status, int epicId,
                   int duration, LocalDateTime startTime) {
        super(type, nameTitle, description, status, duration, startTime);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId = " + epicId +
                ", nameTile = " + getNameTitle() +
                ", description = " + getDescription() +
                ", id = " + getId() +
                ", status = " + getStatus() +
                ", duration = " + getDuration() + " minutes" +
                ", startTime = " + getStartTime() +
                '}' + "\n";
    }
}
