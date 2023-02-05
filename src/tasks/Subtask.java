package tasks;

public class Subtask extends Epic {
    private int epicId;

    public Subtask(String nameTitle, String description, int epicId) {
        super(nameTitle, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }
}
