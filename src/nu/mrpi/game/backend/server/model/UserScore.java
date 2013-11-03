package nu.mrpi.game.backend.server.model;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class UserScore implements Comparable<UserScore> {
    public final int userId;
    public final int score;

    public UserScore(int userId, int score) {
        this.userId = userId;
        this.score = score;
    }

    @Override
    public int compareTo(UserScore other) {
        return other.score - score;
    }
}
