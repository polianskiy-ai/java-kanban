package service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import model.Epic;
import model.Subtask;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HttpTaskServer {
    private static final int PORT = 8081;
    public TaskManager taskManager;
    private Gson gson;
    private HttpServer server;

    public HttpTaskServer() throws IOException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext(String.valueOf(Endpoint.TASKS), this::tasksProcessor);
        server.createContext(String.valueOf(Endpoint.EPICS), this::epicProcessor);
        server.createContext(String.valueOf(Endpoint.SUBTASKS), this::subtaskProcessor);
        server.createContext(String.valueOf(Endpoint.HISTORY), this::historyProcessor);
        server.createContext(String.valueOf(Endpoint.EPIC_SUBTASK), this::epicSubtaskProcessor);
        server.createContext(String.valueOf(Endpoint.PRIORITY), this::prioritizedTaskProcessor);
        taskManager = Manager.getHttpTaskManager();

        gson = new GsonBuilder()
                .serializeNulls()
                .create();
    }

    public void start() {
        server.start();
    }

    private void tasksProcessor(HttpExchange h) throws IOException {
        String method = h.getRequestMethod();
        String query = h.getRequestURI().getQuery();
        switch (method) {
            case "GET":
                if (query == null) {
                    List<Task> taskList = taskManager.getTasks();
                    if (taskList != null) {
                        String responseBody = gson.toJson(taskList);
                        h.sendResponseHeaders(200, 0);
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(responseBody.getBytes());
                        }
                    } else {
                        h.sendResponseHeaders(204, 0);
                    }
                }

                assert query != null;
                if (query.startsWith("id=")) {
                    String[] strings = query.split("&")[0].split("=");
                    int id = Integer.parseInt(strings[1]);
                    Task task = taskManager.getTaskById(id);
                    if (task != null) {
                        String responseBody = gson.toJson(task);
                        h.sendResponseHeaders(200, 0);
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(responseBody.getBytes());
                        }
                    } else {
                        h.sendResponseHeaders(204, 0);
                    }
                } else {
                    h.sendResponseHeaders(404, 0);
                }
                h.close();
                break;

            case "POST":
                InputStream inputStream = h.getRequestBody();
                String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                Task taskFromJson = gson.fromJson(requestBody, Task.class);
                if (taskFromJson != null) {
                    System.out.println("Попытка добавления");
                    taskManager.addTask(taskFromJson);
                    h.sendResponseHeaders(201, 0);
                    String responseBody = "ID созданной задачи " + taskFromJson.getId();
                    try (OutputStream os = h.getResponseBody()) {
                        os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    h.sendResponseHeaders(422, 0);
                    String responseBody = "Введен некорректный формат задачи";
                    try (OutputStream os = h.getResponseBody()) {
                        os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                    }
                }
                h.close();
                break;

            case "DELETE":
                if (query == null) {
                    taskManager.deleteAllTasks();
                    h.sendResponseHeaders(200, 0);
                }
                assert query != null;
                if (query.startsWith("id=")) {
                    String[] strings = query.split("&")[0].split("=");
                    int id = Integer.parseInt(strings[1]);

                    if (taskManager.getTaskById(id) != null) {
                        taskManager.deleteTaskById(id);
                        h.sendResponseHeaders(200, 0);
                        String responseBody = gson.toJson("Задача удалена");
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                        }
                    } else {
                        h.sendResponseHeaders(404, 0);
                    }
                } else {
                    h.sendResponseHeaders(400, 0);
                }

                h.close();
                break;

            default:
                h.sendResponseHeaders(501, 0);
                h.close();
                break;
        }
    }

    private void epicProcessor(HttpExchange h) throws IOException {
        String method = h.getRequestMethod();
        String query = h.getRequestURI().getQuery();
        switch (method) {
            case "GET":
                if (query == null) {
                    List<Epic> epicList = taskManager.getEpics();
                    if (epicList != null) {
                        String responseBody = gson.toJson(epicList);
                        h.sendResponseHeaders(200, 0);
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(responseBody.getBytes());
                        }
                    } else {
                        h.sendResponseHeaders(204, 0);
                    }
                }

                assert query != null;
                if (query.startsWith("id=")) {
                    String[] strings = query.split("&")[0].split("=");
                    int id = Integer.parseInt(strings[1]);
                    Epic epic = taskManager.getEpicById(id);
                    if (epic != null) {
                        String responseBody = gson.toJson(epic);
                        h.sendResponseHeaders(200, 0);
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(responseBody.getBytes());
                        }
                    } else {
                        h.sendResponseHeaders(204, 0);
                    }
                } else {
                    h.sendResponseHeaders(404, 0);
                }
                h.close();
                break;

            case "POST":
                InputStream inputStream = h.getRequestBody();
                String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                Epic epicFromJson = gson.fromJson(requestBody, Epic.class);
                if (epicFromJson != null) {
                    taskManager.addEpic(epicFromJson);
                    h.sendResponseHeaders(201, 0);
                    String responseBody = "ID созданной задачи " + epicFromJson.getId();
                    try (OutputStream os = h.getResponseBody()) {
                        os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    h.sendResponseHeaders(422, 0);
                    String responseBody = "Введен некорректный формат задачи";
                    try (OutputStream os = h.getResponseBody()) {
                        os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                    }
                }
                h.close();
                break;

            case "DELETE":
                if (query == null) {
                    taskManager.deleteAllEpics();
                    h.sendResponseHeaders(200, 0);
                }
                assert query != null;
                if (query.startsWith("id=")) {
                    String[] strings = query.split("&")[0].split("=");
                    int id = Integer.parseInt(strings[1]);

                    if (taskManager.getEpicById(id) != null) {
                        taskManager.deleteEpicById(id);
                        h.sendResponseHeaders(200, 0);
                        String responseBody = gson.toJson("Задача удалена");
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                        }
                    } else {
                        h.sendResponseHeaders(404, 0);
                    }
                } else {
                    h.sendResponseHeaders(400, 0);
                }
                h.close();
                break;

            default:
                h.sendResponseHeaders(501, 0);
                h.close();
                break;
        }
    }

    private void subtaskProcessor(HttpExchange h) throws IOException {
        String method = h.getRequestMethod();
        String query = h.getRequestURI().getQuery();
        switch (method) {
            case "GET":
                if (query == null) {
                    List<Subtask> subtaskList = taskManager.getSubtasks();
                    if (subtaskList != null) {
                        String responseBody = gson.toJson(subtaskList);
                        h.sendResponseHeaders(200, 0);
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(responseBody.getBytes());
                        }
                    } else {
                        h.sendResponseHeaders(204, 0);
                    }
                }

                assert query != null;
                if (query.startsWith("id=")) {
                    String[] strings = query.split("&")[0].split("=");
                    int id = Integer.parseInt(strings[1]);
                    Subtask subtask = taskManager.getSubtaskById(id);
                    if (subtask != null) {
                        String responseBody = gson.toJson(subtask);
                        h.sendResponseHeaders(200, 0);
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(responseBody.getBytes());
                        }
                    } else {
                        h.sendResponseHeaders(204, 0);
                    }
                } else {
                    h.sendResponseHeaders(404, 0);
                }
                h.close();
                break;

            case "POST":
                InputStream inputStream = h.getRequestBody();
                String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                String epic = requestBody.split("===")[0];
                String subtask = requestBody.split("===")[1];
                Epic epicFromJson = gson.fromJson(epic, Epic.class);
                Subtask subtaskFromJson = gson.fromJson(subtask, Subtask.class);
                if (subtaskFromJson != null) {
                    taskManager.addSubtask(subtaskFromJson);
                    h.sendResponseHeaders(201, 0);
                    String responseBody = "ID созданной задачи " + subtaskFromJson.getId();
                    try (OutputStream os = h.getResponseBody()) {
                        os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                    }
                } else {
                    h.sendResponseHeaders(422, 0);
                    String responseBody = "Введен некорректный формат задачи";
                    try (OutputStream os = h.getResponseBody()) {
                        os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                    }
                }
                h.close();
                break;

            case "DELETE":
                if (query == null) {
                    taskManager.deleteAllSubtasks();
                    h.sendResponseHeaders(200, 0);
                }
                assert query != null;
                if (query.startsWith("id=")) {
                    String[] strings = query.split("&")[0].split("=");
                    int id = Integer.parseInt(strings[1]);

                    if (taskManager.getSubtaskById(id) != null) {
                        taskManager.deleteSubtaskById(id);
                        h.sendResponseHeaders(200, 0);
                        String responseBody = gson.toJson("Задача удалена");
                        try (OutputStream os = h.getResponseBody()) {
                            os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                        }
                    } else {
                        h.sendResponseHeaders(404, 0);
                    }
                } else {
                    h.sendResponseHeaders(400, 0);
                }
                h.close();
                break;

            default:
                h.sendResponseHeaders(501, 0);
                h.close();
                break;
        }
    }

    private void historyProcessor(HttpExchange h) throws IOException {
        String method = h.getRequestMethod();
        String query = h.getRequestURI().getQuery();
        if ("GET".equals(method)) {
            if (query == null) {
                List<Task> taskList = taskManager.getHistory();
                String responseBody = gson.toJson(taskList);
                h.sendResponseHeaders(200, 0);
                try (OutputStream os = h.getResponseBody()) {
                    os.write(responseBody.getBytes());
                }
            } else {
                h.sendResponseHeaders(404, 0);
            }
        } else {
            h.sendResponseHeaders(501, 0);
        }
        h.close();
    }

    private void epicSubtaskProcessor(HttpExchange h) throws IOException {
        String method = h.getRequestMethod();
        String query = h.getRequestURI().getQuery();
        if ("GET".equals(method)) {
            if (query == null) {
                h.sendResponseHeaders(422, 0);
                String responseBody = "Введен некорректный формат задачи";
                try (OutputStream os = h.getResponseBody()) {
                    os.write(responseBody.getBytes(StandardCharsets.UTF_8));
                }
            }

            assert query != null;
            if (query.startsWith("id=")) {
                String[] strings = query.split("&")[0].split("=");
                int id = Integer.parseInt(strings[1]);
                List<Subtask> allSubtaskFromEpic = taskManager.getListSubtaskByEpic(id);
                if (allSubtaskFromEpic != null) {
                    String responseBody = gson.toJson(allSubtaskFromEpic);
                    h.sendResponseHeaders(200, 0);
                    try (OutputStream os = h.getResponseBody()) {
                        os.write(responseBody.getBytes());
                    }
                } else {
                    h.sendResponseHeaders(204, 0);
                }
            } else {
                h.sendResponseHeaders(404, 0);
            }
            h.close();
        }
    }

    private void prioritizedTaskProcessor(HttpExchange h) throws IOException {
        String method = h.getRequestMethod();
        String query = h.getRequestURI().getQuery();
        if ("GET".equals(method)) {
            if (query == null) {
                List<Task> taskList = taskManager.getPrioritizedTasks();
                String responseBody = gson.toJson(taskList);
                h.sendResponseHeaders(200, 0);
                try (OutputStream os = h.getResponseBody()) {
                    os.write(responseBody.getBytes());
                }
            } else {
                h.sendResponseHeaders(404, 0);
            }
        } else {
            h.sendResponseHeaders(501, 0);
        }
        h.close();
    }

    public void stop() {
        server.stop(1);
    }
}

