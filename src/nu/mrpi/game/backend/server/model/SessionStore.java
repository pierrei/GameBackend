package nu.mrpi.game.backend.server.model;

import nu.mrpi.game.backend.server.utils.TimeProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class SessionStore {
    static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final long SESSION_EXPIRATION_TIME = 10 * 60 * 1000;
    static final int SESSION_ID_LENGTH = 8;

    private static final Random random = new Random();

    private final Map<Integer, Session> activeSessions = new HashMap<>();

    private final TimeProvider timeProvider;

    public SessionStore(final TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public String createSession(int userId) {
        String sessionId = generateSessionId();

        activeSessions.put(userId, new Session(userId, sessionId, timeProvider.now()));

        return sessionId;
    }

    public String getSessionId(int userId) {
        Session session = activeSessions.get(userId);
        if (session != null) {
            if (!hasExpired(session)) {
                return session.getSessionId();
            } else {
                activeSessions.remove(userId);
            }
        }
        return null;
    }

    public void cleanUpSessions() {
        for (Session session : activeSessions.values()) {
            if (hasExpired(session)) {
                activeSessions.remove(session.getUserId());
            }
        }
    }

    private boolean hasExpired(Session session) {
        return timeProvider.now() >= session.getCreateTime() + SESSION_EXPIRATION_TIME;
    }

    private String generateSessionId() {
        StringBuilder stringBuilder = new StringBuilder(SESSION_ID_LENGTH);

        for (int i = 0; i < SESSION_ID_LENGTH; i++)
            stringBuilder.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));

        return stringBuilder.toString();
    }
}
