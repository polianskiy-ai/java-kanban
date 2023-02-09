package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    protected ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(String nameTitle, String description) {
        super(nameTitle, description);
    }

    public ArrayList<Integer> getSubtasksId() {
        return subtasksId;
    }

    public void setSubtasksId(ArrayList<Integer> subtasksId) {
        this.subtasksId = subtasksId;
    }

    public ArrayList<Integer> listId(){
        return subtasksId;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "nameTitle = " + getNameTitle() +
                ", description = " + getDescription() +
                ", id = " + getId() +
                ", status = " +  getStatus() +
                "}" + "\n";
    }
}
