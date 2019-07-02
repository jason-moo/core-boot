package com.xzn.ons;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;

/**
 * ONS 生产者接口
 *
 */
public interface ONSProducerService {
    /**
     * 同步发布消息到mq
     *
     * @param message 消息
     */
    SendResult publish(Message message);


    /**
     * 异步发送消息到mq
     *
     * @param message  消息
     * @param callback 回调
     */
    void publishAsync(Message message, SendCallback callback);

}
