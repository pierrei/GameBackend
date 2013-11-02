package nu.mrpi.game.backend.server.modules;

import com.sun.net.httpserver.HttpExchange;
import nu.mrpi.game.backend.server.model.SessionStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginWebModuleTest {
    private static final String SESSION_ID = "SESSIONID";
    private static final long MAX_USER_ID = 2147483647L;

    private LoginWebModule loginModule;

    @Mock
    private HttpExchange request;

    @Mock
    private OutputStream outputStream;

    @Mock
    private SessionStore sessionStore;

    @Before
    public void setUp() throws Exception {
        loginModule = new LoginWebModule(sessionStore);
        when(request.getResponseBody()).thenReturn(outputStream);
        when(sessionStore.createSession(anyInt())).thenReturn(SESSION_ID);
    }

    @Test
    public void handlesCorrectLoginUrl() throws Exception {
        assertTrue(loginModule.handlesPath(new URI("/1/login")));
    }

    @Test
    public void handlesCorrectLoginUrlWithLargestUserId() throws Exception {
        assertTrue(loginModule.handlesPath(new URI("/" + MAX_USER_ID + "/login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithLargestUserIdPlusOne() throws Exception {
        assertFalse(loginModule.handlesPath(new URI("/" + (MAX_USER_ID + 1L) + "/login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithNegativeUserId() throws Exception {
        assertFalse(loginModule.handlesPath(new URI("/-123123123/login")));
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
    public void handleGoodRequestCreatesSession() throws Exception {
        setUpRequestURI("/10/login");

        loginModule.handleRequest(request);

        verifyOkSent(SESSION_ID);
        verify(sessionStore).createSession(10);
    }

    private void setUpRequestURI(String path) {
        try {
            when(request.getRequestURI()).thenReturn(new URI(path));
        } catch (URISyntaxException e) {
            // Ignore
        }
    }

    private void verifyOkSent(String responseBody) throws IOException {
        verify(request).sendResponseHeaders(200, responseBody.length());
        verify(outputStream).write(responseBody.getBytes());
        verify(outputStream).close();
    }
}
