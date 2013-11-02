package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class WebServerTest {
    private WebServer server;

    @Mock
    private ServerFactory serverFactory;

    @Mock
    private HttpServer httpServer;

    @Before
    public void setUp() throws Exception {
        when(serverFactory.createHttpServer(anyInt())).thenReturn(httpServer);

        server = new WebServer(serverFactory);
    }

    @Test
    public void testStartServer() throws Exception {
        server.startServer();

        verify(serverFactory).createHttpServer(anyInt());
        verify(httpServer).start();
    }
}
