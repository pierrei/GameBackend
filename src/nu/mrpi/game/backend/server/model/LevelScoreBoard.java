package nu.mrpi.game.backend.server.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class LevelScoreBoard {
    private static final int MAX_SCORES_PER_LEVEL = 15;

    private Map<Integer, Integer> scores = new TreeMap<>();

    public void updateUserScore(int userId, int score) {
        Integer currentUserScore = scores.get(userId);
        if (currentUserScore == null || score > currentUserScore) {

            if (scores.size() < MAX_SCORES_PER_LEVEL)
                scores.put(userId, score);
        }
    }

    public Map<Integer, Integer> getLevelScoreBoardAsMap() {
        return scores;
    }
}
