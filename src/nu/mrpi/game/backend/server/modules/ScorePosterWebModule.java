package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.HttpMethod;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Pattern;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ScorePosterWebModule extends AbstractWebModule {
    private static final String PATH_REGEXP = "^/(\\d+)/score";
    private static final Pattern PATH_PATTERN = Pattern.compile(PATH_REGEXP);

    @Override
    public boolean handlesPath(HttpMethod method, URI path) {
        try {
            int userId = getUserIdFromURI(path, PATH_PATTERN);

            return method.equals(HttpMethod.POST) && isValidUserId(userId);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        sendOkResponse(httpExchange, "");
    }
}
