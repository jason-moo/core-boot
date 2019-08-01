package com.xzn.session;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义的session会话
 */
public class XSession {

    private String userId;

    private Map<String, Object> attributes = new HashMap<>();

    public void setAttribute(String key, Object value) {
        this.attributes.put(key, value);
    }

    public Object getAttribute(String key) {
        return this.attributes.get(key);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
