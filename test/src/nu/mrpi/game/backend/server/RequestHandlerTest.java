package nu.mrpi.game.backend.server;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.modules.WebModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.*;

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

        verifyNotFoundSent();
    }

    @Test
    public void delegatesToAcceptingModule() throws Exception {
        WebModule acceptingModule = createAcceptingModule();

        RequestHandler requestHandler = new RequestHandler(Arrays.asList(acceptingModule));

        requestHandler.handle(request);

        verify(acceptingModule).handleRequest(request);
    }

    @Test
    public void delegatesToFirstAcceptingModule() throws Exception {
        WebModule notAcceptingModule = createNotAcceptingModule();
        WebModule acceptingModule = createAcceptingModule();

        RequestHandler requestHandler = new RequestHandler(Arrays.asList(notAcceptingModule, acceptingModule));

        requestHandler.handle(request);

        verify(acceptingModule).handleRequest(request);
    }

    @Test
    public void fallbackOnDefaultWhenNoModuleAccepted() throws Exception {
        WebModule notAcceptingModule = createNotAcceptingModule();
        WebModule otherNotAcceptingModule = createNotAcceptingModule();

        RequestHandler requestHandler = new RequestHandler(Arrays.asList(notAcceptingModule, otherNotAcceptingModule));

        requestHandler.handle(request);

        verifyNotFoundSent();
    }

    private void verifyNotFoundSent() throws IOException {
        verify(request).sendResponseHeaders(404, NOT_FOUND.length());
        verify(outputStream).write(NOT_FOUND.getBytes());
        verify(outputStream).close();
    }

    private WebModule createAcceptingModule() {
        return createModule(true);
    }

    private WebModule createNotAcceptingModule() {
        return createModule(false);
    }

    private WebModule createModule(boolean accepting) {
        WebModule webModule = mock(WebModule.class);
        when(webModule.handlesPath(Matchers.<URI>anyObject())).thenReturn(accepting);
        return webModule;
    }
}
