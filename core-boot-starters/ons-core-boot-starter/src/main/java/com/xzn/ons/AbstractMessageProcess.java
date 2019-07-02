package com.xzn.ons;

import com.aliyun.openservices.ons.api.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class AbstractMessageProcess implements MessageProcess {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractMessageProcess.class);

    /**
     * 消息处理
     *
     * @param message 订阅的消息
     */
    @Override
    public void process(Message message) {
        if (logger.isDebugEnabled()) {
            logger.debug("开始消费消息 {},", buildInfoMsg(message));
        }

        try {
            doProcess(message);
        } catch (Exception e) {
            // 2016.6.14 增加处理机制，
            // 若消费者业务抛出异常，消息都会走重试流程，至多重试 16 次，如果重试 16 次后，仍然失败，则消息丢弃
            logger.error("消息" + buildInfoMsg(message) + "消费失败", e);
            throw e;
        }
    }

    /**
     * 实际的消息处理类
     * 请实现此方法
     *
     * @param message 订阅的消息
     */
    protected abstract void doProcess(Message message);

    /**
     * 构建提示消息
     * 这里只是拼接topic和tag
     *
     * @param message 订阅的消息
     * @return error message
     */
    private String buildInfoMsg(Message message) {
        return new StringBuffer("{")
                .append(message.getMsgID())
                .append("_")
                .append(message.getTopic())
                .append("_")
                .append(message.getTag()).append("}")
                .toString();
    }
}
