package com.xzn.ons;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.xzn.common.ensure.Ensure;
import com.xzn.common.execptions.ExceptionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @author ylc
 * @date 2018/1/25
 **/
public class OnsOrderProducerServiceImpl implements OnsOrderProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ONSProducerServiceImpl.class);

    /**
     * ONS配置参数
     */
    Properties properties;

    /**
     * ons default producer
     */
    private OrderProducer producer;


    @Override
    public SendResult publish(Message message, String shardingKey) {
        Ensure.that(message).isNotNull("F_CORE_MQ_1002");
        Ensure.that(shardingKey).isNotNull("F_CORE_MQ_1010");
        SendResult result = null;
        try {
            result = producer.send(message, shardingKey);
            return result;
        } catch (Exception e) {
            logger.error("ons生产者发布顺序消息失败", e);
            throw ExceptionFactory.create("F_CORE_MQ_1003");
        } finally {
            logger.info("publish to ons order =>  Message Id : {} , data : {}",
                    result != null ? result.getMessageId() : "无", message.toString());
        }
    }

    // 初始化方法
    private void init() {

        /** 初始化对象*/
        producer = ONSFactory.createOrderProducer(properties);
        producer.start();
        if (logger.isInfoEnabled()) {
            logger.info("ons order producer start.");
        }
    }

    // 销毁方法
    private void destory() {
        producer.shutdown();
        if (logger.isInfoEnabled()) {
            logger.info("ons order producer shutdown.");
        }
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
