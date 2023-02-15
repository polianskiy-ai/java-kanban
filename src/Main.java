import manager.*;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new InMemoryTaskManager();
        HistoryManager historyManager = new InMemoryHistoryManager();
        Task task1 = new Task("Задача 1", "Описание 1");
        Task task2 = new Task("Задача 2", "Описание 2");
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1", "Подзадача у эпика - 1", TaskStatus.DONE);
        Subtask subtask2 = new Subtask("Подзадача 2", "Подзадача у эпика - 1", TaskStatus.IN_PROGRESS);
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        Subtask subtask3 = new Subtask("Подзадача 3", "Подзадача у эпика - 2", TaskStatus.DONE);
        // первая проверка, создаем задачи:
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1, epic1);
        taskManager.addSubtask(subtask2, epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3, epic2);

        // вторая проверка, распечатываем:
        System.out.println("2 - распечатываем: ");
        System.out.println("История просмотров - " + Manager.getDefaultHistory().getHistory());

        // распечатываем по идентификатору:
        System.out.println("2.1 - распечатываем по идентификатору: ");
        System.out.println(taskManager.getTaskById(1));
        System.out.println(taskManager.getTaskById(2));
        System.out.println(taskManager.getEpicById(3));
        System.out.println(taskManager.getEpicById(6));
        System.out.println(taskManager.getEpicById(3));
        System.out.println(taskManager.getListSubtaskByEpic(epic1));
        System.out.println("История просмотров - " + Manager.getDefaultHistory().getHistory());
        System.out.println("");
    }


}
