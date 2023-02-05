package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subTaskId;

    public Epic(String nameTitle, String description) {
        super(nameTitle, description);
    }

    public ArrayList<Integer> getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(ArrayList<Integer> subTaskId) {
        this.subTaskId = subTaskId;
    }
}
