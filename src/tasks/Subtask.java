package tasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String nameTitle, String description, TaskStatus status) {
        super(nameTitle, description, status);
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
                ", status = " +  getStatus() +
                '}' + "\n";
    }
}
