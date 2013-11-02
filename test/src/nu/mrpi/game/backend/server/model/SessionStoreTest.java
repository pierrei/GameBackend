package nu.mrpi.game.backend.server.model;

import nu.mrpi.game.backend.server.utils.TimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
@RunWith(MockitoJUnitRunner.class)
public class SessionStoreTest {
    private static final long NOW = 1234567890L;
    public static final int TEN_MINUTES_IN_SECONDS = 10 * 60;
    private SessionStore sessionStore;

    @Mock
    private TimeProvider timeProvider;

    @Before
    public void setUp() throws Exception {
        when(timeProvider.now()).thenReturn(NOW);

        sessionStore = new SessionStore(timeProvider);
    }

    @Test
    public void testCreateSessionGeneratesSessionIdLongerThanFiveCharacters() throws Exception {
        String session = sessionStore.createSession(10);

        assertTrue(session.length() > 5);
    }

    @Test
    public void testCreateSessionGeneratesSessionWithOnlyUpperCaseCharacters() throws Exception {
        String session = sessionStore.createSession(10);

        for (char c : session.toCharArray()) {
            assertTrue(c + " was not a uppercase letter", Character.isUpperCase(c));
        }
    }

    @Test
    public void testGetSessionReturnsNullForUserWithoutSession() throws Exception {
        assertNull(sessionStore.getSessionId(10));
    }

    @Test
    public void testGetSessionReturnsPreviouslyGeneratedSessionIdForSameUser() throws Exception {
        String firstSession = sessionStore.createSession(10);

        String storedSession = sessionStore.getSessionId(10);

        assertEquals(storedSession, firstSession);
    }

    @Test
    public void testCreateSessionGeneratesUniqueIds() throws Exception {
        Set<String> generatedSessionIds = new HashSet<>();

        for (int i = 0; i < 100000; i++) {
            String session = sessionStore.createSession(i);
            if (generatedSessionIds.contains(session)) {
                fail("Generated key \"" + session + "\" already generated before");
            }
            generatedSessionIds.add(session);
        }
    }

    @Test
    public void testCleanUpSessionsRemovesOldSession() throws Exception {
        sessionStore.createSession(10);

        forwardClockInSeconds(TEN_MINUTES_IN_SECONDS + 1);

        sessionStore.cleanUpSessions();

        assertNull("No session should exist for user", sessionStore.getSessionId(10));
    }

    @Test
    public void testCleanUpSessionsDoesNotRemoveAlmostTooOldSession() throws Exception {
        String sessionId = sessionStore.createSession(10);

        forwardClockInSeconds(TEN_MINUTES_IN_SECONDS - 1);

        sessionStore.cleanUpSessions();

        String storedSessionId = sessionStore.getSessionId(10);
        assertNotNull(storedSessionId);
        assertEquals(storedSessionId, sessionId);
    }

    @Test
    public void testGetSessionExpiresTooOldSession() throws Exception {
        sessionStore.createSession(10);

        forwardClockInSeconds(TEN_MINUTES_IN_SECONDS + 1);

        String storedSession = sessionStore.getSessionId(10);

        assertNull(storedSession);
    }

    private void forwardClockInSeconds(int seconds) {
        when(timeProvider.now()).thenReturn(NOW + (seconds * 1000));
    }
}
