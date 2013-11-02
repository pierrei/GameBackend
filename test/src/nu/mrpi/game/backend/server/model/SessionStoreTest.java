package nu.mrpi.game.backend.server.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
            assertTrue(Character.isUpperCase(c));
        }
    }
}
