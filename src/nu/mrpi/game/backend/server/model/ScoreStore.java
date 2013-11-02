package nu.mrpi.game.backend.server.model;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ScoreStore {
    public void addScore(int score, int level, int userId) {
        System.out.println("Added score " + score + " for level " + level + " for user " + userId);
    }
}
