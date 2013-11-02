package nu.mrpi.game.backend;

import nu.mrpi.game.backend.server.DefaultServerFactory;
import nu.mrpi.game.backend.server.WebServer;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class Main {
    public static void main(String[] args) {
        new WebServer(new DefaultServerFactory()).startServer(8080);
    }
}
