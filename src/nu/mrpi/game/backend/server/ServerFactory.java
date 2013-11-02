package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public interface ServerFactory {
    HttpServer createHttpServer(int port) throws IOException;
}
