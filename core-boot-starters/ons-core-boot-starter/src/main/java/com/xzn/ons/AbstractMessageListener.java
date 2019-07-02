package com.xzn.ons;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 静态消息监听类
 *
 */
public abstract class AbstractMessageListener implements MessageListener {

    public static final Logger logger = LoggerFactory.getLogger(AbstractMessageListener.class);

    /**
     * 消息监听方法
     *
     * @param message        订阅的消息
     * @param consumeContext
     * @return
     */
    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        logger.debug("开始消费消息.");

        doConsume(message);

        return Action.CommitMessage;
    }

    /**
     * 实际消息处理方法
     *
     * @param message
     */
    protected abstract void doConsume(Message message);
}
