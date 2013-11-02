package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpServer;
import nu.mrpi.game.backend.server.modules.ModuleFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class WebServerTest {
    private static final int PORT = 8080;

    private WebServer server;

    @Mock
    private ServerFactory serverFactory;

    @Mock
    private ModuleFactory moduleFactory;

    @Mock
    private HttpServer httpServer;

    @Before
    public void setUp() throws Exception {
        when(serverFactory.createHttpServer(PORT)).thenReturn(httpServer);

        server = new WebServer(serverFactory, moduleFactory);
    }

    @Test
    public void testStartServer() throws Exception {
        server.startServer(PORT);

        verify(serverFactory).createHttpServer(PORT);
        verify(httpServer).start();
    }

    @Test
    public void testStopServer() throws Exception {
        server.startServer(PORT);
        server.stopServer();

        verify(httpServer).stop(5);
    }
}
