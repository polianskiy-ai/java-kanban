import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();
        Task task1 = new Task("Задача 1", "Описание 1");
        Task task2 = new Task("Задача 2", "Описание 2");
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1", "Подзадача у эпика - 1", TaskStatus.DONE);
        Subtask subtask2 = new Subtask("Подзадача 2", "Подзадача у эпика - 1", TaskStatus.IN_PROGRESS);
        Subtask subtask3 = new Subtask("Подзадача 3", "Подзадача у эпика - 1", TaskStatus.IN_PROGRESS);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        //создаем задачи:
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1, epic1);
        taskManager.addSubtask(subtask2, epic1);
        taskManager.addSubtask(subtask3, epic1);
        taskManager.addEpic(epic2);

        //распечатываем:
        System.out.println("История просмотров - " + Manager.getDefaultHistory().getHistory());

        // запрос задач:
        System.out.println("вызов задач");
        taskManager.getTaskById(2);
        taskManager.getTaskById(1);
        taskManager.getEpicById(7);
        taskManager.getEpicById(3);
        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(4);
        taskManager.getSubtaskById(6);

        System.out.println("История просмотров - " + taskManager.getHistory());

        taskManager.getSubtaskById(5);
        taskManager.getSubtaskById(4);
        taskManager.getSubtaskById(6);
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        taskManager.getEpicById(3);
        taskManager.getEpicById(7);

        System.out.println("История просмотров - " + taskManager.getHistory());

        taskManager.deleteTaskById(1);

        System.out.println("История просмотров - " + taskManager.getHistory());

        taskManager.deleteEpicById(3);
    }


}
