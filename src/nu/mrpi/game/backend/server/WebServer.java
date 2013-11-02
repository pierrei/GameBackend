package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class WebServer {
    private ServerFactory serverFactory;

    public WebServer(final ServerFactory serverFactory) {
        this.serverFactory = serverFactory;
    }

    public void startServer(int port) {
        log("Starting server..");
        try {
            HttpServer httpServer = serverFactory.createHttpServer(port);
            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log("Server started");
    }

    private void log(String line) {
        System.out.println(line);
    }
}
