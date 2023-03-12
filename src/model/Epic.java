package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> subtasksId = new ArrayList<>();

    public Epic(String nameTitle, String description) {
        super(nameTitle, description);
    }

    public List<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void setSubtasksId(int subtasksId) {
        this.subtasksId.add(subtasksId);
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
