import service.HttpTaskServer;
import service.KVServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            new KVServer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpTaskServer server = new HttpTaskServer();
        server.start();
    }


}
