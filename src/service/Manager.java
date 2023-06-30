package service;

import java.io.IOException;

public class Manager {

    private Manager() {
    }

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static HttpTaskManager getHttpTaskManager() throws IOException {
        return new HttpTaskManager(getDefaultHistory());
    }
}
