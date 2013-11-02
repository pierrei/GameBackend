package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.model.SessionStore;

import java.io.IOException;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class LoginWebModule extends AbstractWebModule {
    private static final String PATH_REGEXP = "^/(\\d+)/login$";
    private static final Pattern PATH_PATTERN = Pattern.compile(PATH_REGEXP);

    private SessionStore sessionStore;

    public LoginWebModule(final SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public boolean handlesPath(URI path) {
        return path.getRawPath().matches(PATH_REGEXP);
    }

    @Override
    public void handleRequest(HttpExchange request) throws IOException {
        int userId = getUserIdFromURI(request.getRequestURI());

        sendOkResponse(request, sessionStore.createSession(userId));
    }

    int getUserIdFromURI(URI path) {
        Matcher matcher = PATH_PATTERN.matcher(path.getRawPath());
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalArgumentException("Could not find userId in path");
    }
}
