package nu.mrpi.game.backend.server.modules;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginWebModuleTest {

    private LoginWebModule loginModule;

    @Before
    public void setUp() throws Exception {
        loginModule = new LoginWebModule();
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
}
