package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import nu.mrpi.game.backend.server.modules.NotFoundWebModule;
import nu.mrpi.game.backend.server.modules.WebModule;

import java.io.IOException;
import java.util.List;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class RequestHandler implements HttpHandler {
    private WebModule defaultModule = new NotFoundWebModule();

    private List<WebModule> modules;

    public RequestHandler(List<WebModule> modules) {
        this.modules = modules;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        for (WebModule module : modules) {
            if (module.handlesPath(HttpMethod.fromHttpExchange(httpExchange), httpExchange.getRequestURI())) {
                module.handleRequest(httpExchange);
                return;
            }
        }
        defaultModule.handleRequest(httpExchange);
    }

}
