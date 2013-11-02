package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public abstract class AbstractWebModule implements WebModule {
    static final int MAX_USER_ID = ((int) Math.pow(2.0, 31.0)); // unsigned 31 bit integer

    void sendOkResponse(HttpExchange httpExchange, String response) throws IOException {
        sendResponse(httpExchange, 200, response);
    }

    void sendResponse(HttpExchange httpExchange, int responseCode, String response) throws IOException {
        httpExchange.sendResponseHeaders(responseCode, response.length());

        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    int getUserIdFromURI(URI path, Pattern patternWithUserIdInIt) {
        Matcher matcher = patternWithUserIdInIt.matcher(path.getRawPath());
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalArgumentException("Could not find userId in path");
    }

    boolean isValidUserId(long userId) {
        return userId >= 0 && userId <= MAX_USER_ID;
    }
}
