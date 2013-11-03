package nu.mrpi.game.backend.server.model;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ScoreStore {
    final Map<Integer, LevelScoreBoard> scoreBoard = new ConcurrentHashMap<>();

    public void addScore(int score, int level, int userId) {

        LevelScoreBoard levelScoreBoard = scoreBoard.get(level);
        if (levelScoreBoard == null) {
            levelScoreBoard = new LevelScoreBoard();
            scoreBoard.put(level, levelScoreBoard);
        }

        levelScoreBoard.updateUserScore(userId, score);
    }

    LevelScoreBoard getScoreBoard(int levelId) {
        return scoreBoard.get(levelId);
    }

    public Map<Integer, Integer> getScores(int levelId) {
        System.out.println("Getting scores for level " + levelId);

        LevelScoreBoard levelScoreBoard = scoreBoard.get(levelId);
        if (levelScoreBoard != null) {
            return levelScoreBoard.getLevelScoreBoardAsMap();
        }

        return Collections.emptyMap();
    }
}
