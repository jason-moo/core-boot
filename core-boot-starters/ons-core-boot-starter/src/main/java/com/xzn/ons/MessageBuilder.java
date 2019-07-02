package com.xzn.ons;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.shade.com.alibaba.rocketmq.common.message.MessageConst;

import java.util.HashSet;
import java.util.Set;

public class MessageBuilder {

    /**
     * 消息主题
     */
    private String topic;

    /**
     * 消息标签（目前rocket只支持一个tag）
     *
     * @link MessageConst.PROPERTY_TAGS
     */
    private String tags;

    /**
     * 消息关键字（用于查询消息），多个key使用KEY_SEPARATOR（“ ”）隔开
     *
     * @link MessageConst.PROPERTY_KEYS
     */
    private String keyStr;

    /**
     * 消息关键字集合，不可重复
     */
    private Set<String> keys;

    /**
     * 消息体对象
     */
    private Object body;

    public MessageBuilder topic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder tags(String tags) {
        this.tags = tags;
        return this;
    }

    public MessageBuilder key(String key) {
        if (keys == null) {
            keys = new HashSet<>();
        }
        keys.add(key);
        return this;
    }

    public MessageBuilder body(Object body) {
        this.body = body;
        return this;
    }

    public Message build() {
        if (keys != null && !keys.isEmpty()) {
            // just work in java8 ==> keyStr = keys.stream().collect(Collectors.joining(MessageConst.KEY_SEPARATOR));
            StringBuilder s = new StringBuilder();
            for (String key : keys) {
                s.append(MessageConst.KEY_SEPARATOR).append(key);
            }
            keyStr = s.substring(MessageConst.KEY_SEPARATOR.length());
        }
        return new Message(this.topic, this.tags, this.keyStr, JSON.toJSONBytes(this.body));
    }

    public static MessageBuilder init() {
        return new MessageBuilder();
    }

}
