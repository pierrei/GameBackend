package nu.mrpi.game.backend.server.model;

import java.util.Date;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class Session {
    private int userId;
    private String sessionId;
    private long createTime;

    public Session(int userId, String sessionId, long createTime) {

        this.userId = userId;
        this.sessionId = sessionId;
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getCreateTime() {
        return createTime;
    }
}
