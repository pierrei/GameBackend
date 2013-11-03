package nu.mrpi.game.backend.server;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class Settings {
    public static final int MAX_SCORES_PER_LEVEL = 15;
    public static final String ALLOWED_SESSION_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final long SESSION_EXPIRATION_TIME = 10 * 60 * 1000;
    public static final int SESSION_ID_LENGTH = 8;
    public static final int SECONDS_TO_WAIT_BEFORE_STOPPING = 5;
    public static final int MAX_USER_ID = ((int) Math.pow(2.0, 31.0)); // unsigned 31 bit integer
}
