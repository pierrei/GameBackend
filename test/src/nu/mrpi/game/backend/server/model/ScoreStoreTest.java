package nu.mrpi.game.backend.server.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ScoreStoreTest {
    private ScoreStore scoreStore;

    @Before
    public void setUp() throws Exception {
        scoreStore = new ScoreStore();
    }

    @Test
    public void testAddScoreAddsAScore() throws Exception {
        scoreStore.addScore(10, 1, 1000);

        ScoreBoard scoreBoard = scoreStore.getScoreBoard(1);
        assertNotNull(scoreBoard);
    }
}
