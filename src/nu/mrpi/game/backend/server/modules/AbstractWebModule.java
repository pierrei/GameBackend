package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public abstract class AbstractWebModule implements WebModule {
    void sendOkResponse(HttpExchange httpExchange, String response) throws IOException {
        sendResponse(httpExchange, 200, response);
    }

    void sendResponse(HttpExchange httpExchange, int responseCode, String response) throws IOException {
        httpExchange.sendResponseHeaders(responseCode, response.length());

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
