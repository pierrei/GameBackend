package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class WebServer {
    private static final int SECONDS_TO_WAIT_BEFORE_STOPPING = 5;

    private ServerFactory serverFactory;

    private HttpServer httpServer;

    public WebServer(final ServerFactory serverFactory) {
        this.serverFactory = serverFactory;
    }

    public void startServer(int port) {
        log("Starting server..");

        try {
            httpServer = serverFactory.createHttpServer(port);

            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log("Server started");
    }

    public void stopServer() {
        if (httpServer != null) {
            log("Stopping server (waiting " + SECONDS_TO_WAIT_BEFORE_STOPPING + " seconds to finish existing connections)..");
            httpServer.stop(SECONDS_TO_WAIT_BEFORE_STOPPING);
            log("Server stopped");
        }
    }

    private void log(String line) {
        System.out.println(line);
    }
}
