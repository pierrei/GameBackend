package nu.mrpi.game.backend.server.model;

import nu.mrpi.game.backend.server.Settings;
import nu.mrpi.game.backend.server.utils.TimeProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class SessionStore {
    private static final Random random = new Random();

    private final Map<String, Session> activeSessions = new HashMap<>();

    private final TimeProvider timeProvider;

    public SessionStore(final TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public String createSession(int userId) {
        String sessionKey = generateSessionId();

        synchronized (activeSessions) {
            activeSessions.put(sessionKey, new Session(userId, sessionKey, timeProvider.now()));
        }

        System.out.println("Created session " + sessionKey + " for user " + userId);

        return sessionKey;
    }

    public Session getSession(String sessionKey) {
        synchronized (activeSessions) {
            Session session = activeSessions.get(sessionKey);
            if (session != null) {
                if (!hasExpired(session)) {
                    return session;
                } else {
                    activeSessions.remove(sessionKey);
                }
            }
        }
        return null;
    }

    public void cleanUpSessions() {
        synchronized (activeSessions) {
            for (Session session : activeSessions.values()) {
                if (hasExpired(session)) {
                    activeSessions.remove(session.getSessionKey());
                }
            }
        }
    }

    private boolean hasExpired(Session session) {
        return timeProvider.now() >= session.getCreateTime() + Settings.SESSION_EXPIRATION_TIME;
    }

    private String generateSessionId() {
        StringBuilder stringBuilder = new StringBuilder(Settings.SESSION_ID_LENGTH);

        for (int i = 0; i < Settings.SESSION_ID_LENGTH; i++) {
            char c = Settings.ALLOWED_SESSION_CHARACTERS.charAt(random.nextInt(Settings.ALLOWED_SESSION_CHARACTERS.length()));
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    public boolean isSessionKeyValid(String sessionKey) {
        return getSession(sessionKey) != null;
    }
}
