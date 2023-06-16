import model.*;
import org.junit.jupiter.api.Test;
import service.Manager;
import service.TaskManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {
    TaskManager tm = Manager.getDefault();

    Task task = new Task(TaskType.TASK, "Task", "blabla");
    Epic epic = new Epic(TaskType.EPIC, "Epic", "bla bla");
    Subtask subtask = new Subtask(TaskType.SUBTASK, "Subtask", "bla", TaskStatus.NEW, 2,
            25, LocalDateTime.now());


    @Test
    void testEmptyHistory() {
        assertTrue(tm.getHistory().isEmpty());
    }

    @Test
    void testDoubleHistory() {
        tm.addTask(task);
        tm.addEpic(epic);
        tm.addSubtask(subtask);
        tm.getTaskById(1);
        tm.getEpicById(2);
        tm.getTaskById(1);
        assertEquals(2, tm.getHistory().size());
    }

    @Test
    void mustDeleteTaskInHistoryBegin() {
        tm.addTask(task);
        tm.addEpic(epic);
        tm.addSubtask(subtask);
        tm.getTaskById(1);
        tm.getEpicById(2);
        tm.getSubtaskById(3);
        tm.deleteTaskById(1);
        assertFalse(tm.getHistory().contains(task));
    }

    @Test
    void mustDeleteTaskInHistoryMiddle() {
        tm.addTask(task);
        tm.addEpic(epic);
        tm.addSubtask(subtask);
        tm.getTaskById(1);
        tm.getEpicById(2);
        tm.getSubtaskById(3);
        tm.deleteEpicById(2);
        assertFalse(tm.getHistory().contains(epic));
    }

    @Test
    void mustDeleteTaskInHistoryEnd() {
        tm.addTask(task);
        tm.addEpic(epic);
        tm.addSubtask(subtask);
        tm.getTaskById(1);
        tm.getEpicById(2);
        tm.getSubtaskById(3);
        tm.deleteSubtaskById(3);
        assertFalse(tm.getHistory().contains(subtask));
    }

}