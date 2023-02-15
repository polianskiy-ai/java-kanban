package manager;

public class Manager {
    static InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();
    static InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

    static TaskManager getDefault() {
        return inMemoryTaskManager;
    }

    public static HistoryManager getDefaultHistory() {
        return inMemoryHistoryManager;
    }
}
