package com.xzn.session;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的session会话
 * Created by liuliling on 17/6/13.
 */
public class XSession {

    private String sessionId;
    private String userId;
    private String server;

    private Map<String, Object> attributes = new HashMap<>();

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        if (attributes != null) {
            this.attributes = attributes;
        }
    }
}
