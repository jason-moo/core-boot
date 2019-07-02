package com.xzn.ons;

import com.aliyun.openservices.ons.api.*;
import com.xzn.common.ensure.Ensure;
import com.xzn.common.execptions.ExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * ONS 生产者接口实现类
 *
 */
public class ONSProducerServiceImpl implements ONSProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ONSProducerServiceImpl.class);

    /**
     * ONS配置参数
     */
    private Properties properties;

    /**
     * ons default producer
     */
    private Producer producer;

    /**
     * 发布消息
     *
     * @param message 消息
     */
    @Override
    public SendResult publish(Message message) {
        Ensure.that(message).isNotNull("F_CORE_MQ_1002");
        /**此处抓取运行时异常，主要包括无法连接Broker，发送消息超时，Broker返回异常，客户端异常等 */
        SendResult result = null;
        try {
            result = producer.send(message);
            return result;
        } catch (Exception e) {
            logger.error("ons生产者发布消息失败", e);
            throw ExceptionFactory.create("F_CORE_MQ_1003");
        } finally {
            logger.info("publish to ons =>  Message Id : {} , data : {}",
                    result != null ? result.getMessageId() : "无", message.toString());
        }
    }

    @Override
    public void publishAsync(Message message, SendCallback callback) {
        Ensure.that(message).isNotNull("F_CORE_MQ_1002");
        /**此处抓取运行时异常，主要包括无法连接Broker，发送消息超时，Broker返回异常，客户端异常等 */
        SendResult result = null;
        try {
            producer.sendAsync(message, callback);
        } catch (Exception e) {
            logger.error("ons生产者发布消息失败", e);
            throw ExceptionFactory.create("F_CORE_MQ_1003");
        } finally {
            logger.info("publish to ons =>  Message Id : {} , data : {}",
                    result != null ? result.getMessageId() : "无", message.toString());
        }

    }


    // 初始化方法
    private void init() {

        /** 初始化对象*/
        producer = ONSFactory.createProducer(properties);
        producer.start();
        if (logger.isInfoEnabled()) {
            logger.info("ons producer start.");
        }
    }

    // 销毁方法
    private void destory() {
        producer.shutdown();
        if (logger.isInfoEnabled()) {
            logger.info("ons producer shutdown.");
        }
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
