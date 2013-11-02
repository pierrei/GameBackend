package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.mockito.Mock;

import java.io.IOException;
import java.io.OutputStream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class AbstractWebModuleTest {
    static final long MAX_USER_ID = 2147483647L;

    @Mock
    HttpExchange request;

    @Mock
    OutputStream outputStream;

    @Before
    public void setUp() throws Exception {
        when(request.getResponseBody()).thenReturn(outputStream);
    }

    void verifyOkSent(String responseBody) throws IOException {
        verify(request).sendResponseHeaders(200, responseBody.length());
        verify(outputStream).write(responseBody.getBytes());
        verify(outputStream).close();
    }
}
