package nu.mrpi.game.backend.server.model;

/**
 * @author Pierre Ingmansson (pierre@ingmansson.com)
 */
public class Session {
    private int userId;
    private String sessionKey;
    private long createTime;

    public Session(int userId, String sessionKey, long createTime) {

        this.userId = userId;
        this.sessionKey = sessionKey;
        this.createTime = createTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public long getCreateTime() {
        return createTime;
    }
}
