package nu.mrpi.game.backend.server.modules;

import nu.mrpi.game.backend.server.model.SessionStore;

import java.util.Arrays;
import java.util.List;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ModuleFactory {
    private SessionStore sessionStore;

    public ModuleFactory(final SessionStore sessionStore) {
        this.sessionStore = sessionStore;
    }

    public List<WebModule> createModules() {
        return Arrays.<WebModule>asList(new LoginWebModule(sessionStore), new ScorePosterWebModule(sessionStore));
    }
}
