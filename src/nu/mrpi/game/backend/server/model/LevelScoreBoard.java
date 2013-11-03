package nu.mrpi.game.backend.server.model;

import java.util.Map;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public interface LevelScoreBoard {
    void updateUserScore(int userId, int score);

    Map<Integer, Integer> getLevelScoreBoardAsMap();
}
