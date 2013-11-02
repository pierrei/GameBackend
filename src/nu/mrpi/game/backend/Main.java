package nu.mrpi.game.backend;

import nu.mrpi.game.backend.server.DefaultServerFactory;
import nu.mrpi.game.backend.server.WebServer;
import nu.mrpi.game.backend.server.model.SessionStore;
import nu.mrpi.game.backend.server.modules.ModuleFactory;
import nu.mrpi.game.backend.server.utils.TimeProvider;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class Main {
    private static final DefaultServerFactory serverFactory = new DefaultServerFactory();
    private static final TimeProvider timeProvider = new TimeProvider();
    private static final SessionStore sessionStore = new SessionStore(timeProvider);
    private static final ModuleFactory moduleFactory = new ModuleFactory(sessionStore);

    private static final WebServer webServer = new WebServer(serverFactory, moduleFactory);

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
