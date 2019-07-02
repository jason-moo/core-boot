package com.xzn.ons;


import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.xzn.common.execptions.ExceptionFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class MessageHandlerFactory {

    public static final Logger logger = LoggerFactory.getLogger(MessageHandlerFactory.class);

    /**
     * 消息费集群列表
     */
    private List<ConsumerConfig> consumerConfigs;

    /**
     * 普通消费者集合
     */
    private List<Consumer> consumerList = new ArrayList<>();

    private Map<String, Map<String, MessageProcess>> processAllMap = new HashMap<>();
    /**
     * 顺序消费者集合
     */
    private List<OrderConsumer> orderConsumerList = new ArrayList<>();

    /**
     * 是否开启消息费
     */
    private boolean enabled = true;


    // 初始化方法
    public void init() {
        if (!enabled) {
            logger.info("aliyun ons consumer not work. What exactly are you trying to disable? ");
            return;
        }
        if (CollectionUtils.isEmpty(consumerConfigs)) {
            return;
        }
        for (ConsumerConfig config : consumerConfigs) {
            initConsumer(config);
        }
    }

    private void initConsumer(ConsumerConfig config) {
        //无配置信息直接势抛出异常
        if (config.getProperties() == null) {
            throw ExceptionFactory.create("F_CORE_MQ_1008");
        }
        //获取消费者集群ID
        String consumerId = config.getProperties().getProperty("ConsumerId");
        if (StringUtils.isBlank(consumerId)) {
            throw ExceptionFactory.create("F_CORE_MQ_1009");
        }
        //获取所有Topic
        LinkedHashMap<String, String> map = getTopicList(config);
        //存储所有消费业务处理类
        processAllMap.put(consumerId, config.getSupportMessageProcess());
        if (!config.getOrder()) {
            //初始化普通消费者
            Consumer consumer = ONSFactory.createConsumer(config.getProperties());
            //注册处理类
            subscribe(map, consumer, consumerId);
            //启动
            consumer.start();
            consumerList.add(consumer);
        } else {
            //初始化顺序消息消费者处理
            OrderConsumer orderConsumer = ONSFactory.createOrderedConsumer(config.getProperties());
            //注册处理类
            subscribe(map, orderConsumer, consumerId);
            //启动
            orderConsumer.start();
            orderConsumerList.add(orderConsumer);
        }


    }

    /**
     * 获取所有的Topic
     *
     * @param config
     * @return
     */
    public LinkedHashMap<String, String> getTopicList(ConsumerConfig config) {
        LinkedHashMap<String, String> hmap = new LinkedHashMap<>();
        // 初始化支持的消息处理类对应的topic/tag，并且自动启动相应的消费类（消费类按照topic订阅）
        if (config.getSupportMessageProcess() != null && !config.getSupportMessageProcess().isEmpty()) {
            for (String key : config.getSupportMessageProcess().keySet()) {
                // 校验key是否符合规范，即是否以topic_tag的组合为key,且topic和tag都不为空
                validateKey(key);
                //获取topic
                String topic = getTopic(key);
                String tag = getTag(key);
                if (!hmap.containsKey(topic)) {
                    hmap.put(topic, tag);
                } else {
                    hmap.put(topic, hmap.get(topic) + "||" + tag);
                }
            }
        }
        return hmap;
    }

    public void subscribe(LinkedHashMap<String, String> map, final Consumer consumer, final String consumerId) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            // 订阅topic下的tags || 分隔
            consumer.subscribe(entry.getKey(), entry.getValue(), new AbstractMessageListener() {
                @Override
                public void doConsume(Message message) {
                    createProcess(consumerId, message.getTopic(), message.getTag()).process(message);
                }
            });
        }
    }

    public void subscribe(LinkedHashMap<String, String> map, final OrderConsumer orderConsumer, final String consumerId) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            // 订阅topic下的tags || 分隔
            orderConsumer.subscribe(entry.getKey(), entry.getValue(), new AbstractMessageOrderListener() {
                @Override
                public void doConsume(Message message) {
                    createProcess(consumerId, message.getTopic(), message.getTag()).process(message);
                }
            });
        }
    }

    /**
     * 根据tag获取具体的消息处理类
     *
     * @param topic 主题
     * @param tag   标签
     * @return MessageProcess
     */
    private MessageProcess createProcess(String consumerId, String topic, String tag) {
        String key = topic + ONSContants.MESSAGEHANDLER_KEY_SEPARATOR + tag;

        MessageProcess messageProcess = null;

        Map<String, MessageProcess> processMap = processAllMap.get(consumerId);
        if (processMap != null && processMap.containsKey(key)) {
            messageProcess = processMap.get(key);
        }
        if (messageProcess == null) {
            // 发布了相应的topic+tag的消息，却没有配置该消息的处理类
            logger.error("发布了{}的消息，却没有配置该消息的处理类!", key);
            messageProcess = DefaultMessageProcess.getInstance();
        }
        return messageProcess;
    }


    /**
     * 校验配置的消息处理类对应的key是否符合规范：
     * 是否以topic@tag的组合为key,且topic和tag都不为空
     *
     * @param key
     */
    private void validateKey(String key) {
        if (!key.contains(ONSContants.MESSAGEHANDLER_KEY_SEPARATOR)
                || StringUtils.isBlank(key.split(ONSContants.MESSAGEHANDLER_KEY_SEPARATOR)[0])
                || StringUtils.isBlank(key.split(ONSContants.MESSAGEHANDLER_KEY_SEPARATOR)[1])
                || "*".equals(key.split(ONSContants.MESSAGEHANDLER_KEY_SEPARATOR)[1])
                ) {
            throw ExceptionFactory.create("F_CORE_MQ_1007");
        }
    }

    /**
     * 从key中拆出主题topic
     */
    private String getTopic(String key) {
        return key.split(ONSContants.MESSAGEHANDLER_KEY_SEPARATOR)[0];
    }

    /**
     * 从key中拆除标签tag
     */
    private String getTag(String key) {
        return key.split(ONSContants.MESSAGEHANDLER_KEY_SEPARATOR)[1];
    }

    public void setConsumerConfigs(List<ConsumerConfig> consumerConfigs) {
        this.consumerConfigs = consumerConfigs;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
