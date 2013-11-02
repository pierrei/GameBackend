package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.HttpMethod;
import nu.mrpi.game.backend.server.model.ScoreStore;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class HighScoreListWebModule extends AbstractWebModule {
    private static final String PATH_REGEXP = "^/(\\d+)/highscorelist$";
    private static final Pattern PATH_PATTERN = Pattern.compile(PATH_REGEXP);
    private ScoreStore scoreStore;

    public HighScoreListWebModule(final ScoreStore scoreStore) {
        this.scoreStore = scoreStore;
    }

    @Override
    public boolean handlesPath(HttpMethod method, URI path) {
        try {
            int levelId = getLevelId(path);

            return method.equals(HttpMethod.GET) && isValid31BitUnsignedInteger(levelId);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public void handleRequest(HttpExchange httpExchange) throws IOException {
        int levelId = getLevelId(httpExchange.getRequestURI());

        Map<Integer,Integer> scores = scoreStore.getScores(levelId);

        sendOkResponse(httpExchange, convertToCSV(scores));
    }

    private String convertToCSV(Map<Integer, Integer> scores) {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            result.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1) : "";
    }

    private int getLevelId(URI path) {
        return getIdFromURI(path, PATH_PATTERN);
    }

}
