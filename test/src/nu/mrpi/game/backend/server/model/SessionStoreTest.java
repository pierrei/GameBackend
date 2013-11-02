package nu.mrpi.game.backend.server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class SessionStoreTest {
    private SessionStore sessionStore;

    @Before
    public void setUp() throws Exception {
        sessionStore = new SessionStore();
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
}
