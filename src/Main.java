import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

public class Main {

    public static void main(String[] args) {
    TaskManager taskManager = new TaskManager();
    Task task1 = new Task("Задача 1", "Описание 1", TaskStatus.NEW);
    Task task2 = new Task("Задача 2", "Описание 2", TaskStatus.NEW);
    Epic epic1 = new Epic("Эпик 1","Описание эпика 1");
    Subtask subtask1 = new Subtask("Подзадача 1","Подзадача у эпика - 1", TaskStatus.IN_PROGRESS, epic1.getId());
    Subtask subtask2 = new Subtask("Подзадача 2","Подзадача у эпика - 1", TaskStatus.IN_PROGRESS, epic1.getId());
    Epic epic2 = new Epic("Эпик 2","Описание эпика 2");
    Subtask subtask3 = new Subtask("Подзадача 3","Подзадача у эпика - 2", TaskStatus.DONE, epic1.getId());
    // первая проверка, создаем задачи:
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addEpic(epic1);
        taskManager.addSubtask(subtask1, epic1);
        taskManager.addSubtask(subtask2, epic1);
        taskManager.addEpic(epic2);
        taskManager.addSubtask(subtask3, epic2);
        System.out.println("");

    // вторая проверка, распечатываем:
        taskManager.getAllTasks();
        System.out.println("");

    // третья проверка, меняем статусы и обновляем:
        taskManager.changeTaskStatus(task2, TaskStatus.IN_PROGRESS);
        taskManager.updateTask(2,"Задача 2 обновленная","обновление");
        taskManager.getAllTasks();
        System.out.println("");

    // четвертая проверка, удаление:
        taskManager.deleteById(1);
        taskManager.deleteById(6);
        taskManager.getAllTasks();
        System.out.println(" ");
    }


}
