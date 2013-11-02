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
public class LoginWebModule extends AbstractWebModule {
    private static final String PATH_REGEXP = "^/(\\d+)/login$";
    private static final Pattern PATH_PATTERN = Pattern.compile(PATH_REGEXP);

    static final int MAX_USER_ID = ((int) Math.pow(2.0, 31.0)); // unsigned 31 bit integer

    private SessionStore sessionStore;

    public LoginWebModule(final SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    @Override
    public boolean handlesPath(HttpMethod method, URI path) {
        try {
            int userId = getUserIdFromURI(path, PATH_PATTERN);
            
            return method.equals(HttpMethod.GET) && userId >= 0 && userId <= MAX_USER_ID;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void handleRequest(HttpExchange request) throws IOException {
        int userId = getUserIdFromURI(request.getRequestURI(), PATH_PATTERN);

        sendOkResponse(request, sessionStore.createSession(userId));
    }

}
