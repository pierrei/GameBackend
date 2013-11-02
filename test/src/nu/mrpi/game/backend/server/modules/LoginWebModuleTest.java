package nu.mrpi.game.backend.server.modules;

import nu.mrpi.game.backend.server.HttpMethod;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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
public class LoginWebModuleTest extends AbstractWebModuleTest {
    private static final String SESSION_ID = "SESSIONID";

    private LoginWebModule loginModule;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        loginModule = new LoginWebModule(sessionStore);
        when(sessionStore.createSession(anyInt())).thenReturn(SESSION_ID);
    }

    @Test
    public void handlesCorrectLoginUrl() throws Exception {
        assertTrue(loginModule.handlesPath(HttpMethod.GET, new URI("/1/login")));
    }

    @Test
    public void handlesCorrectLoginUrlWithLargestUserId() throws Exception {
        assertTrue(loginModule.handlesPath(HttpMethod.GET, new URI("/" + MAX_ID + "/login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithLargestUserIdPlusOne() throws Exception {
        assertFalse(loginModule.handlesPath(HttpMethod.GET, new URI("/" + (MAX_ID + 1L) + "/login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithNegativeUserId() throws Exception {
        assertFalse(loginModule.handlesPath(HttpMethod.GET, new URI("/-123123123/login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithoutUserId() throws Exception {
        assertFalse(loginModule.handlesPath(HttpMethod.GET, new URI("//login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithUserIdThatIsNotANumber() throws Exception {
        assertFalse(loginModule.handlesPath(HttpMethod.GET, new URI("/user/login")));
    }

    @Test
    public void doesNotHandleLoginUrlWithTrailingSlash() throws Exception {
        assertFalse(loginModule.handlesPath(HttpMethod.GET, new URI("/10/login/")));
    }

    @Test
    public void doesNotHandleLoginUrlWithWrongMethod() throws Exception {
        assertFalse(loginModule.handlesPath(HttpMethod.POST, new URI("/10/login")));
    }

    @Test
    public void handleGoodRequestCreatesSession() throws Exception {
        setUpRequestPath("/10/login");

        loginModule.handleRequest(request);

        verifyOkSent(SESSION_ID);
        verify(sessionStore).createSession(10);
    }
}
