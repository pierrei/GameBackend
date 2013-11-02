package nu.mrpi.game.backend;

import nu.mrpi.game.backend.server.DefaultServerFactory;
import nu.mrpi.game.backend.server.WebServer;
import nu.mrpi.game.backend.server.modules.ModuleFactory;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class Main {
    private static final WebServer webServer = new WebServer(new DefaultServerFactory(), new ModuleFactory());

    public static void main(String[] args) {
        registerShutdownHook();

        webServer.startServer(8080);
    }

    private static void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                webServer.stopServer();
            }
        });
    }
}
