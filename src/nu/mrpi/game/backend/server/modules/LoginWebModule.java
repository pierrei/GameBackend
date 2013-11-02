package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URI;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class LoginWebModule extends AbstractWebModule {
    @Override
    public boolean handlesPath(URI path) {
        return path.getRawPath().matches("^/\\d+/login$");
    }

    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        sendOkResponse(httpExchange, "Logged in!");
    }
}
