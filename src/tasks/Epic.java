package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(String nameTitle, String description) {
        super(nameTitle, description);
    }

    public ArrayList<Integer> getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(ArrayList<Integer> subtaskId) {
        this.subtaskId = subtaskId;
    }

    public ArrayList<Integer> listId(){
        return subtaskId;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "nameTitle = " + getNameTitle() +
                ", description = " + getDescription() +
                ", id = " + getId() +
                ", status = " +  getStatus() +
                "}";
    }
}
