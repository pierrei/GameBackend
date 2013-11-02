package nu.mrpi.game.backend.server.modules;

import nu.mrpi.game.backend.server.HttpMethod;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertTrue;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ScorePosterWebModuleTest {
    private static final long MAX_USER_ID = 2147483647L;

    private ScorePosterWebModule scorePoster;

    @Before
    public void setUp() throws Exception {
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
}
