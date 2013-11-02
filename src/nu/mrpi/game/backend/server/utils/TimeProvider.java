package nu.mrpi.game.backend.server.utils;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class TimeProvider {
    public long now() {
        return System.currentTimeMillis();
    }
}
