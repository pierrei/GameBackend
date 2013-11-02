package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.HttpMethod;
import nu.mrpi.game.backend.server.model.ScoreStore;
import nu.mrpi.game.backend.server.model.Session;
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
    private ScoreStore scoreStore;

    public ScorePosterWebModule(final SessionStore sessionStore, ScoreStore scoreStore) {
        this.sessionStore = sessionStore;
        this.scoreStore = scoreStore;
    }

    @Override
    public boolean handlesPath(HttpMethod method, URI path) {
        try {
            int levelId = getLevelId(path);

            return method.equals(HttpMethod.POST) && isValid31BitUnsignedInteger(levelId);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        String sessionKey = getParameter(httpExchange, "sessionkey");

        Session session = sessionStore.getSession(sessionKey);
        if (session != null) {
            int score = getNumberFromBody(httpExchange);

            if (!isValid31BitUnsignedInteger(score)) {
                sendResponse(httpExchange, 400, "Bad request");
                return;
            }

            int levelId = getLevelId(httpExchange.getRequestURI());

            scoreStore.addScore(score, levelId, session.getUserId());

            sendOkResponse(httpExchange, "");
            return;
        }

        sendResponse(httpExchange, 403, "Access denied");
    }

    private int getLevelId(URI path) {
        return getIdFromURI(path, PATH_PATTERN);
    }

    private int getNumberFromBody(HttpExchange httpExchange) {
        String body = getBody(httpExchange);
        try {
            return Integer.parseInt(body);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
