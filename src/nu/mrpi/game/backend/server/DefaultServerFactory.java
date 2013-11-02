package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class DefaultServerFactory implements ServerFactory{
    @Override
    public HttpServer createHttpServer(int port) throws IOException {
        return HttpServer.create(new InetSocketAddress(port), 0);
    }
}
