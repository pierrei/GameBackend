package nu.mrpi.game.backend.server.modules;

import java.util.Arrays;
import java.util.List;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class ModuleFactory {
    public List<WebModule> createModules() {
        return Arrays.<WebModule>asList(new NotFoundWebModule());
    }
}
