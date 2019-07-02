package com.xzn.ons;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConsumerConfig {

    /**
     * ONS配置参数
     */
    private Properties properties;

    /**
     * 是否顺序消费者，默认否
     */
    private Boolean order = Boolean.FALSE;

    /**
     * 支持的mq消息处理类
     * key:topic_tag
     */
    private Map<String, MessageProcess> supportMessageProcess = new HashMap<>();

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Map<String, MessageProcess> getSupportMessageProcess() {
        return supportMessageProcess;
    }

    public void setSupportMessageProcess(Map<String, MessageProcess> supportMessageProcess) {
        this.supportMessageProcess = supportMessageProcess;
    }

    public Boolean getOrder() {
        return order;
    }

    public void setOrder(Boolean order) {
        this.order = order;
    }
}
