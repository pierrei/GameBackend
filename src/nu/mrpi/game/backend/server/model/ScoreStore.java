package nu.mrpi.game.backend.server.model;

import java.util.Collections;
import java.util.Map;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ScoreStore {
    public void addScore(int score, int level, int userId) {
        System.out.println("Added score " + score + " for level " + level + " for user " + userId);
    }

    public Map<Integer, Integer> getScores(int levelId) {
        System.out.println("Getting scores for level " + levelId);
        return Collections.emptyMap();
    }
}
