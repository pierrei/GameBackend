package nu.mrpi.game.backend.server.modules;

import nu.mrpi.game.backend.server.HttpMethod;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;

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
        scorePoster = new ScorePosterWebModule();
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
        when(request.getRequestURI()).thenReturn(new URI("/10/score?sessionkey=AAA"));

        scorePoster.handleRequest(request);

        verifyOkSent("");
    }
}
