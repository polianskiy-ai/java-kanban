package service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVTaskClient {
    private String urlServer;
    private String apiToken;

    public KVTaskClient() {
        this.urlServer = "http://localhost:" + KVServer.PORT;
        apiToken = getApiToken();

    }

    private String getApiToken() {
        URI uriRegister = URI.create(urlServer + "/register");

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uriRegister)
                .header("Content-Type", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, handler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return apiToken = response.body();
    }

    public void put(String key, String json) {
        URI uriSave = URI.create(urlServer + "/save" + key + "?API_TOKEN=" + apiToken);

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uriSave)
                .header("Content-Type", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, handler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String load(String key) {
        URI uriLoad = URI.create(urlServer + "/load" + key + "?API_TOKEN=" + apiToken);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uriLoad)
                .header("Content-Type", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();

        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, handler);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }
}
