package nu.mrpi.game.backend.server.modules;

import nu.mrpi.game.backend.server.HttpMethod;
import nu.mrpi.game.backend.server.model.ScoreStore;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class HighScoreListWebModuleTest extends AbstractWebModuleTest {
    @Mock
    private ScoreStore scoreStore;

    private HighScoreListWebModule highScoreList;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        highScoreList = new HighScoreListWebModule(scoreStore);
    }

    @Test
    public void handlesCorrectPath() throws Exception {
        assertTrue(highScoreList.handlesPath(HttpMethod.GET, new URI("/1/highscorelist")));
    }

    @Test
    public void handlesCorrectPathWithLargestUserId() throws Exception {
        assertTrue(highScoreList.handlesPath(HttpMethod.GET, new URI("/" + MAX_ID + "/highscorelist")));
    }

    @Test
    public void handleRequestReturnsOkWithCorrectLevelIdAsBody() throws Exception {
        setUpRequestPath("/10/highscorelist");
        when(scoreStore.getScores(10)).thenReturn(createMap(123, 456));

        highScoreList.handleRequest(request);

        verifyOkSent("123=456");
    }

    private Map<Integer, Integer> createMap(int userId, int score) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(userId, score);
        return map;
    }
}
