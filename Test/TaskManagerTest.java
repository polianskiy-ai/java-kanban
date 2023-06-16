import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.Manager;
import service.TaskManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class TaskManagerTest{
    TaskManager tm = Manager.getDefault();

    Task task = new Task(TaskType.TASK, "Task", "blabla");
    Epic epic = new Epic(TaskType.EPIC, "Epic", "bla bla");
    Subtask subtask = new Subtask(TaskType.SUBTASK, "Subtask", "bla", TaskStatus.NEW, 2,
            35, LocalDateTime.now());

    @BeforeEach
    void addAllTasksForTest() {
        tm.addTask(this.task);
        tm.addEpic(this.epic);
        tm.addSubtask(this.subtask);
    }
    @Test
    void testGetTasks() {
        List<Task> task = new ArrayList<>();
        task.add(this.task);
        assertEquals(task, tm.getTasks());
    }

    @Test
    void testGetEpics() {
        List<Epic> epic = new ArrayList<>();
        epic.add(this.epic);
        assertEquals(epic, tm.getEpics());
    }

    @Test
    void testGetSubtasks() {
        List<Subtask> subtask = new ArrayList<>();
        subtask.add(this.subtask);
        assertEquals(subtask, tm.getSubtasks());

    }

    @Test
    void testDeleteAllTasks() {
        assertTrue(tm.getTasks().contains(this.task));
        tm.deleteAllTasks();
        assertTrue(tm.getTasks().isEmpty());

    }

    @Test
    void deleteAllEpics() {
        assertTrue(tm.getEpics().contains(this.epic));
        tm.deleteAllEpics();
        assertTrue(tm.getEpics().isEmpty());
    }

    @Test
    void deleteAllSubtasks() {
        assertTrue(tm.getSubtasks().contains(this.subtask));
        tm.deleteAllSubtasks();
        assertTrue(tm.getSubtasks().isEmpty());
    }

    @Test
    void testGetTaskById() {
        assertEquals(this.task, tm.getTaskById(1));
    }

    @Test
    void testGetTaskByIdWithWrongId() {
        assertThrows(IllegalArgumentException.class, () -> tm.getTaskById(10));
    }

    @Test
    void testGetEpicById() {
        assertEquals(this.epic, tm.getEpicById(1));
    }

    @Test
    void testGetEpicByIdWithWrongId() {
        assertThrows(IllegalArgumentException.class, () -> tm.getEpicById(20));
    }

    @Test
    void testGetSubtaskById() {
        assertEquals(this.subtask, tm.getSubtaskById(1));
    }

    @Test
    void testGetSubtaskByIdWithWrongId() {
        assertThrows(IllegalArgumentException.class, () -> tm.getSubtaskById(30));
    }

    @Test
    void testAddTask() {
        assertTrue(tm.getTasks().contains(this.task));
    }

    @Test
    void testAddNullTask() {
        assertThrows(NullPointerException.class, () -> tm.addTask(null));
    }

    @Test
    void testAddEpic() {
        assertTrue(tm.getEpics().contains(this.epic));
    }

    @Test
    void testAddNullEpic() {
        assertThrows(NullPointerException.class, () -> tm.addEpic(null));
    }

    @Test
    void testAddSubtask() {
        assertTrue(tm.getSubtasks().contains(this.subtask));
    }

    @Test
    void testAddNullSubtask() {
        assertThrows(NullPointerException.class, () -> tm.addSubtask(null));
    }

    @Test
    void testUpdateTask() {
        Task newTask = new Task(TaskType.TASK, "newTask", "new bla bla");
        tm.updateTask(1, newTask);
        assertEquals(newTask, tm.getTaskById(1));
    }

    @Test
    void testUpdateEpic() {
        Epic newEpic = new Epic(TaskType.EPIC, "newEpic", "new bla bla");
        tm.updateEpic(2, newEpic);
        assertEquals(newEpic, tm.getEpicById(2));
    }

    @Test
    void testUpdateSubtask() {
        Subtask newSubtask = new Subtask(TaskType.SUBTASK, "newSubtask", "new bla bla",
                TaskStatus.IN_PROGRESS, 2, 5, LocalDateTime.now());
        tm.updateSubtask(3, newSubtask);
        assertEquals(newSubtask, tm.getSubtaskById(3));
    }

    @Test
    void testDeleteTaskById() {
        tm.deleteTaskById(task.getId());
        assertFalse(tm.getTasks().contains(this.task));
    }

    @Test
    void testDeleteEpicById() {
        tm.deleteEpicById(epic.getId());
        assertFalse(tm.getEpics().contains(this.epic));
    }

    @Test
    void testDeleteSubtaskById() {
        tm.deleteSubtaskById(subtask.getId());
        assertFalse(tm.getSubtasks().contains(this.subtask));
    }

    @Test
    void testGetListSubtaskByEpic() {
        Subtask subtask2 = new Subtask(TaskType.SUBTASK, "Subtask2", "bla", TaskStatus.NEW, 2,
                5, LocalDateTime.now());
        tm.addSubtask(subtask2);
        List<Subtask> result = tm.getListSubtaskByEpic(2);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

}