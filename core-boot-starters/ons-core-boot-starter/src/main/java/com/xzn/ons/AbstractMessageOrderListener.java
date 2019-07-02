package com.xzn.ons;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.order.ConsumeOrderContext;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 静态顺序消息监听类
 *
 **/
public abstract class AbstractMessageOrderListener implements MessageOrderListener {

    public static final Logger logger = LoggerFactory.getLogger(AbstractMessageOrderListener.class);


    /**
     * 消息监听方法
     *
     * @param message 订阅的消息
     * @param context
     * @return
     */
    @Override
    public OrderAction consume(Message message, ConsumeOrderContext context) {
        logger.debug("开始消费消息.");

        doConsume(message);

        return OrderAction.Success;
    }


    /**
     * 实际消息处理方法
     *
     * @param message
     */
    protected abstract void doConsume(Message message);
}
