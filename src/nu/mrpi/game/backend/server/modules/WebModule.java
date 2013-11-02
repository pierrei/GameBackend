package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;

import java.net.URI;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public interface WebModule {
    boolean handlesPath(URI path);
    void handleRequest(HttpExchange httpExchange);
}
