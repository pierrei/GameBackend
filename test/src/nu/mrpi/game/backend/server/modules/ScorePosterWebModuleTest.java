package nu.mrpi.game.backend.server.modules;

import nu.mrpi.game.backend.server.HttpMethod;
import nu.mrpi.game.backend.server.model.ScoreStore;
import nu.mrpi.game.backend.server.model.Session;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class ScorePosterWebModuleTest extends AbstractWebModuleTest {
    private ScorePosterWebModule scorePoster;

    @Mock
    private ScoreStore scoreStore;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        scorePoster = new ScorePosterWebModule(sessionStore, scoreStore);
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
    public void handleRequestReturnsOkWithCorrectLevelIdAsBody() throws Exception {
        addValidSessionForUser(5, "AAA");
        setUpRequestPath("/10/score?sessionkey=AAA");
        setUpRequestBody("1");

        scorePoster.handleRequest(request);

        verifyOkSent("");
    }

    @Test
    public void handleRequestDeniesAccessForInvalidSessionKey() throws Exception {
        setUpRequestPath("/20/score?sessionkey=AAA");
        setUpRequestBody("");

        scorePoster.handleRequest(request);

        verifyAccessDeniedSent();
    }

    @Test
    public void handleRequestDeniesRequestWithEmptyBody() throws Exception {
        addValidSessionForUser(10, "AAA");
        setUpRequestPath("/30/score?sessionkey=AAA");
        setUpRequestBody("");

        scorePoster.handleRequest(request);

        verifyBadRequestSent();
    }

    @Test
    public void handleRequestDeniesRequestWithNonNumericBody() throws Exception {
        addValidSessionForUser(10, "AAA");
        setUpRequestPath("/40/score?sessionkey=AAA");
        setUpRequestBody("BB");

        scorePoster.handleRequest(request);

        verifyBadRequestSent();
    }

    @Test
    public void handleRequestAddsScoreToALevel() throws Exception {
        addValidSessionForUser(10, "AAA");
        setUpRequestPath("/50/score?sessionkey=AAA");
        setUpRequestBody("100");

        scorePoster.handleRequest(request);

        verifyOkSent("");
        verify(scoreStore).addScore(100, 50, 10);
    }

    private void verifyBadRequestSent() throws IOException {
        verifySent(400, "Bad request");
    }

    private void setUpRequestBody(String body) {
        when(request.getRequestBody()).thenReturn(new ByteArrayInputStream(body.getBytes()));
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
}
