package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginWebModuleTest {

    private LoginWebModule loginModule;

    @Mock
    private HttpExchange request;

    @Mock
    private OutputStream outputStream;


    @Before
    public void setUp() throws Exception {
        loginModule = new LoginWebModule();
        when(request.getResponseBody()).thenReturn(outputStream);
    }

    @Test
    public void handlesCorrectLoginUrl() throws Exception {
        assertTrue(loginModule.handlesPath(new URI("/1/login")));
    }

    @Test
    public void handlesCorrectLoginUrlWithHugeUserId() throws Exception {
        assertTrue(loginModule.handlesPath(new URI("/123123123/login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithoutUserId() throws Exception {
        assertFalse(loginModule.handlesPath(new URI("//login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithUserIdThatIsNotANumber() throws Exception {
        assertFalse(loginModule.handlesPath(new URI("/user/login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithTrailingSlash() throws Exception {
        assertFalse(loginModule.handlesPath(new URI("/10/login/")));
    }

    @Test
    public void handleRequestReturnsOk() throws Exception {
        loginModule.handleRequest(request);

        verifyOkSent("Logged in!");
    }

    private void verifyOkSent(String responseBody) throws IOException {
        verify(request).sendResponseHeaders(200, responseBody.length());
        verify(outputStream).write(responseBody.getBytes());
        verify(outputStream).close();
    }
}
