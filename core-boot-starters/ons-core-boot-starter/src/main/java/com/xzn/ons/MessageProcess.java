package com.xzn.ons;

import com.aliyun.openservices.ons.api.Message;

public interface MessageProcess {

    /**
     * 处理消息
     *
     * @param message 订阅的消息
     */
    void process(Message message);
}
