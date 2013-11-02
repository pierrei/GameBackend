package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.modules.WebModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.OutputStream;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class RequestHandlerTest {
    public static final String NOT_FOUND = "Not found";
    @Mock
    private HttpExchange request;

    @Mock
    private OutputStream outputStream;

    @Before
    public void setUp() throws Exception {
        when(request.getResponseBody()).thenReturn(outputStream);
    }

    @Test
    public void usesNotFoundModuleAsDefault() throws Exception {
        RequestHandler requestHandler = new RequestHandler(Collections.<WebModule>emptyList());

        requestHandler.handle(request);

        verify(request).sendResponseHeaders(404, NOT_FOUND.length());
        verify(outputStream).write(NOT_FOUND.getBytes());
        verify(outputStream).close();
    }
}
