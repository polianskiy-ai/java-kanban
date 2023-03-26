package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksId = new ArrayList<>();

    public Epic(TaskType type, String nameTitle, String description) {
        super(type, nameTitle, description);
    }

    public List<Integer> getSubtasksId() {
        return subtasksId;
    }

    public final void addSubtasksId(int subtasksId) {
        this.subtasksId.add(subtasksId);
    }

    public void deleteSubtasks() {
        subtasksId.clear();
    }

    @Override
    public String toString() {
        return "Epic{" +
                "nameTitle = " + getNameTitle() +
                ", description = " + getDescription() +
                ", id = " + getId() +
                ", status = " + getStatus() +
                "}" + "\n";
    }
}
