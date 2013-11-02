package nu.mrpi.game.backend.server.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class LevelScoreBoardTest {
    private LevelScoreBoard levelScoreBoard;

    @Before
    public void setUp() throws Exception {
        levelScoreBoard = new LevelScoreBoard();
    }

    @Test
    public void updateUserScoreKeepsOnlyOneRecordPerUser() throws Exception {
        levelScoreBoard.updateUserScore(1, 10);
        levelScoreBoard.updateUserScore(1, 20);

        assertEquals(1, levelScoreBoard.getLevelScoreBoardAsMap().size());
    }
}
