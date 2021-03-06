package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.Settings;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    int getIdFromURI(URI path, Pattern patternWithUserIdInIt) {
        Matcher matcher = patternWithUserIdInIt.matcher(path.getRawPath());
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        throw new IllegalArgumentException("Could not find userId in path");
    }

    boolean isValid31BitUnsignedInteger(long userId) {
        return userId >= 0 && userId <= Settings.MAX_USER_ID;
    }

    String getBody(HttpExchange httpExchange) {
        try {
            return new Scanner(httpExchange.getRequestBody(), "UTF-8").next();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    String getParameter(HttpExchange httpExchange, String parameter) {
        Map<String, String> parameters = queryToMap(httpExchange.getRequestURI().getQuery());

        return parameters.get(parameter);
    }

    Map<String, String> queryToMap(String query) {
        Map<String, String> result = new HashMap<>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }
}
