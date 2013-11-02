package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public static HttpMethod fromHttpExchange(HttpExchange httpExchange) {
        String requestMethod = httpExchange.getRequestMethod();

        for (HttpMethod httpMethod : values()) {
            if (httpMethod.toString().equals(requestMethod)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException("HTTP method " + requestMethod + " not recognized");
    }
}
