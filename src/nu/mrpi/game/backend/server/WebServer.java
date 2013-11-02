package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpServer;
import nu.mrpi.game.backend.server.modules.ModuleFactory;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class WebServer {
    private static final int SECONDS_TO_WAIT_BEFORE_STOPPING = 5;

    private ServerFactory serverFactory;
    private ModuleFactory moduleFactory;

    private HttpServer httpServer;

    public WebServer(final ServerFactory serverFactory, final ModuleFactory moduleFactory) {
        this.serverFactory = serverFactory;
        this.moduleFactory = moduleFactory;
    }

    public void startServer(int port) {
        log("Starting server..");

        try {
            httpServer = serverFactory.createHttpServer(port);

            httpServer.createContext("/", new RequestHandler(moduleFactory.createModules()));
            httpServer.setExecutor(Executors.newCachedThreadPool());

            httpServer.start();

            log("Server started");
        } catch (IOException e) {
            log("Server failed to start: " + e.getMessage());
        }
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
