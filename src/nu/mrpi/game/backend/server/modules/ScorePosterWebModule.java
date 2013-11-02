package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.HttpMethod;
import nu.mrpi.game.backend.server.model.SessionStore;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Pattern;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ScorePosterWebModule extends AbstractWebModule {
    private static final String PATH_REGEXP = "^/(\\d+)/score";
    private static final Pattern PATH_PATTERN = Pattern.compile(PATH_REGEXP);

    private SessionStore sessionStore;

    public ScorePosterWebModule(final SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public boolean handlesPath(HttpMethod method, URI path) {
        try {
            int levelId = getIdFromURI(path, PATH_PATTERN);

            return method.equals(HttpMethod.POST) && isValidId(levelId);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        String sessionKey = getParameter(httpExchange, "sessionkey");

        if (sessionStore.isSessionKeyValid(sessionKey)) {
            sendOkResponse(httpExchange, "");
            return;
        }

        sendResponse(httpExchange, 403, "Access denied");
    }
}
