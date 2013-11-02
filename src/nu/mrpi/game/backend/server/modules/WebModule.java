package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.HttpMethod;

import java.io.IOException;
import java.net.URI;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public interface WebModule {
    boolean handlesPath(HttpMethod method, URI path);
    void handleRequest(HttpExchange httpExchange) throws IOException;
}
