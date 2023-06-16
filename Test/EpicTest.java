import model.Epic;
import model.Subtask;
import model.TaskStatus;
import model.TaskType;
import org.junit.jupiter.api.Test;
import service.Manager;
import service.TaskManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    TaskManager tm = Manager.getDefault();
    private final Epic epic = new Epic(TaskType.EPIC, "Epic", "zero");
    private final Subtask subtask = new Subtask(TaskType.SUBTASK, "Subtask", "zero",
            TaskStatus.NEW, 1, 5, LocalDateTime.now());
    private final Subtask subtask2 = new Subtask(TaskType.SUBTASK, "Subtask2", "zero",
            TaskStatus.NEW, 1, 10, LocalDateTime.now());

    @Test
    void mustWriteEpicStatusNewWithoutSubtask() {
        tm.addEpic(epic);
        assertEquals(TaskStatus.NEW, tm.getEpicById(1).getStatus());
    }

    @Test
    void mustWriteEpicStatusNewWhenSubtasksNew() {
        tm.addEpic(epic);
        tm.addSubtask(subtask);
        tm.addSubtask(subtask2);
        assertEquals(TaskStatus.NEW, tm.getEpicById(1).getStatus());
    }

    @Test
    void mustWriteEpicStatusDoneWhenSubtasksDone() {
        tm.addEpic(epic);
        subtask.setStatus(TaskStatus.DONE);
        subtask2.setStatus(TaskStatus.DONE);
        tm.addSubtask(subtask);
        tm.addSubtask(subtask2);
        assertEquals(TaskStatus.DONE, tm.getEpicById(1).getStatus());
    }

    @Test
    void mustWriteEpicStatusDoneWhenSubtasksNewAndDone() {
        tm.addEpic(epic);
        subtask2.setStatus(TaskStatus.DONE);
        tm.addSubtask(subtask);
        tm.addSubtask(subtask2);
        assertEquals(TaskStatus.IN_PROGRESS, tm.getEpicById(1).getStatus());
    }

    @Test
    void mustWriteEpicStatusDoneWhenSubtasksInProgress() {
        tm.addEpic(epic);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
            subtask2.setStatus(TaskStatus.IN_PROGRESS);
        tm.addSubtask(subtask);
        tm.addSubtask(subtask2);
        assertEquals(TaskStatus.IN_PROGRESS, tm.getEpicById(1).getStatus(), "EpicTest 5");
    }
}