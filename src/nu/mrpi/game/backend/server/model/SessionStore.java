package nu.mrpi.game.backend.server.model;

import java.util.Random;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class SessionStore {
    static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final int SESSION_ID_LENGTH = 8;

    static final Random random = new Random();

    public String createSession(int userId) {
        return generateSessionId();
    }

    private String generateSessionId() {
        StringBuilder stringBuilder = new StringBuilder(SESSION_ID_LENGTH);

        for (int i = 0; i < SESSION_ID_LENGTH; i++)
            stringBuilder.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));

        return stringBuilder.toString();
    }
}
