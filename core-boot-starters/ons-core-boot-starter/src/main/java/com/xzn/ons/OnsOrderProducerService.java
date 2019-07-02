package com.xzn.ons;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;

/**
 * ONS顺序消息产生者
 *
 **/
public interface OnsOrderProducerService {

    /**
     * 同步发送消息到mq
     *
     * @param message 消息
     * @param shardingKey 顺序消息选择因子
     */
    SendResult publish(Message message, String shardingKey);
}
