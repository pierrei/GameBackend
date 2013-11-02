package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.net.URI;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class NotFoundWebModule extends AbstractWebModule {
    @Override
    public boolean handlesPath(URI path) {
        return true;
    }

    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        sendResponse(httpExchange, 404, "Not found");
    }
}
