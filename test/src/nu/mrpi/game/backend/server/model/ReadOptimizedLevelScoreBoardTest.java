package nu.mrpi.game.backend.server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ReadOptimizedLevelScoreBoardTest {
    private LevelScoreBoard levelScoreBoard;

    @Before
    public void setUp() throws Exception {
        levelScoreBoard = new ReadOptimizedLevelScoreBoard();
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

    @Test
    public void updateUserScoreKeepsTrackOfTwoUsersWithTheSameScore() throws Exception {
        levelScoreBoard.updateUserScore(1, 10);
        levelScoreBoard.updateUserScore(2, 10);

        Map<Integer,Integer> scores = levelScoreBoard.getLevelScoreBoardAsMap();
        assertEquals(2, scores.size());
        assertEquals((Integer) 10, scores.get(1));
        assertEquals((Integer) 10, scores.get(2));

    }

    @Test
    public void getLevelScoreBoardAsMapIsOrderedByScore() throws Exception {
        levelScoreBoard.updateUserScore(1, 10);
        levelScoreBoard.updateUserScore(2, 11);
        levelScoreBoard.updateUserScore(3, 9);

        Map<Integer,Integer> scores = levelScoreBoard.getLevelScoreBoardAsMap();
        ArrayList<Map.Entry<Integer,Integer>> scoreList = new ArrayList<>(scores.entrySet());

        verifyScoreEntry(2, 11, scoreList.get(0));
        verifyScoreEntry(1, 10, scoreList.get(1));
        verifyScoreEntry(3, 9, scoreList.get(2));
    }

    @Test
    public void updateUserScoreStoresMaxFifteenUserScoreRecords() throws Exception {
        for (int i = 1; i <= 20; i++) {
            levelScoreBoard.updateUserScore(i, i + 10);
        }

        Map<Integer,Integer> scores = levelScoreBoard.getLevelScoreBoardAsMap();
        assertEquals(15, scores.size());
    }

    @Test
    public void updateUserScoreDropsLowestScoreWhenFullAndAddingNewScore() throws Exception {
        for (int i = 1; i <= 16; i++) {
            levelScoreBoard.updateUserScore(i, i + 10);
        }

        Map<Integer,Integer> scores = levelScoreBoard.getLevelScoreBoardAsMap();
        Integer userId = 16;
        Integer score = 26;
        for (Map.Entry<Integer, Integer> entry : scores.entrySet()) {
            assertEquals(userId, entry.getKey());
            assertEquals(score, entry.getValue());
            userId--;
            score--;
        }
    }

    private void verifyScoreEntry(int expectedUserId, int expectedScore, Map.Entry<Integer, Integer> scoreEntry) {
        assertEquals("UserId was not as expected", (Integer) expectedUserId, scoreEntry.getKey());
        assertEquals("Score was not as expected", (Integer) expectedScore, scoreEntry.getValue());
    }
}
