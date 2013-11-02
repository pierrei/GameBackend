package nu.mrpi.game.backend.server.modules;

import nu.mrpi.game.backend.server.HttpMethod;
import nu.mrpi.game.backend.server.model.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ScorePosterWebModuleTest extends AbstractWebModuleTest {
    private ScorePosterWebModule scorePoster;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        scorePoster = new ScorePosterWebModule(sessionStore);
    }

    @Test
    public void handlesCorrectPath() throws Exception {
        assertTrue(scorePoster.handlesPath(HttpMethod.POST, new URI("/1/score?sessionkey=AAA")));
    }

    @Test
    public void handlesCorrectPathWithLargestUserId() throws Exception {
        assertTrue(scorePoster.handlesPath(HttpMethod.POST, new URI("/" + MAX_USER_ID + "/score?sessionkey=AAA")));
    }

    @Test
    public void handleRequestReturnsOkWithEmptyBody() throws Exception {
        addValidSessionForUser(10, "AAA");
        setUpRequestPath("/10/score?sessionkey=AAA");
        setUpRequestAttribute("sessionkey", "AAA");

        scorePoster.handleRequest(request);

        verifyOkSent("");
    }

    @Test
    public void handleRequestDeniesAccessForInvalidSessionKey() throws Exception {
        setUpRequestPath("/10/score?sessionkey=AAA");

        scorePoster.handleRequest(request);

        verifyAccessDeniedSent();
    }

    private void verifyAccessDeniedSent() throws IOException {
        verifySent(403, "Access denied");
    }

    private void addValidSessionForUser(int userId, String sessionKey) {
        when(sessionStore.getSession(sessionKey)).thenReturn(new Session(userId, sessionKey, 1234567890L));
        when(sessionStore.isSessionKeyValid(sessionKey)).thenReturn(true);
    }

    private void setUpRequestPath(String path) throws URISyntaxException {
        when(request.getRequestURI()).thenReturn(new URI(path));
    }

    private void setUpRequestAttribute(String key, String value) {
        when(request.getAttribute(key)).thenReturn(value);
    }
}
