package nu.mrpi.game.backend.server.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class LevelScoreBoard {
    private Map<Integer, Integer> scores = new TreeMap<>();

    public void updateUserScore(int userId, int score) {
        Integer currentUserScore = scores.get(userId);
        if (currentUserScore == null || score > currentUserScore) {
            scores.put(userId, score);
        }
    }

    public Map<Integer, Integer> getLevelScoreBoardAsMap() {
        return scores;
    }
}
