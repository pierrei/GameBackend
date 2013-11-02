package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.model.SessionStore;
import org.junit.Before;
import org.mockito.Mock;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class AbstractWebModuleTest {
    static final long MAX_ID = 2147483647L;

    @Mock
    HttpExchange request;

    @Mock
    OutputStream outputStream;

    @Mock
    SessionStore sessionStore;

    @Before
    public void setUp() throws Exception {
        when(request.getResponseBody()).thenReturn(outputStream);
    }

    void verifyOkSent(String responseBody) throws IOException {
        verifySent(200, responseBody);
    }

    void verifySent(int responseCode, String responseBody) throws IOException {
        verify(request).sendResponseHeaders(responseCode, responseBody.length());
        verify(outputStream).write(responseBody.getBytes());
        verify(outputStream).close();
    }

    protected void setUpRequestPath(String path) throws URISyntaxException {
        when(request.getRequestURI()).thenReturn(new URI(path));
    }
}
