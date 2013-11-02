package nu.mrpi.game.backend.server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

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

    @Test
    public void updateUserScoreKeepsTheHighestScoreForOneUser() throws Exception {
        levelScoreBoard.updateUserScore(1, 10);
        levelScoreBoard.updateUserScore(1, 20);
        levelScoreBoard.updateUserScore(1, 11);

        Map<Integer,Integer> scores = levelScoreBoard.getLevelScoreBoardAsMap();
        assertEquals(1, scores.size());
        assertEquals((Integer) 20, scores.get(1));
    }

    @Test
    public void updateUserScoreKeepsScoresForMoreThanOneUser() throws Exception {
        levelScoreBoard.updateUserScore(1, 10);
        levelScoreBoard.updateUserScore(2, 20);

        Map<Integer,Integer> scores = levelScoreBoard.getLevelScoreBoardAsMap();
        assertEquals(2, scores.size());
        assertEquals((Integer) 10, scores.get(1));
        assertEquals((Integer) 20, scores.get(2));
    }
}
