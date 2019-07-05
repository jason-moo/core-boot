package com.xzn.ons.prop;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@ConfigurationProperties(prefix = "xkeshi.ons.consumer")
@Validated
public class OnsConsumerProperties {

    private boolean enable = false;

    @NotEmpty(message = "ons consumer的集群配置不能为空")
    @Valid
    private List<ConsumerConfigProperties> consumerConfigs;

    /**
     * consumer集群配置
     */
    public static class ConsumerConfigProperties {

        @NotBlank(message = "ons consumer集群配置的consumerId不能为空")
        private String consumerId;

        @NotBlank(message = "ons consumer集群配置的accessKey不能为空")
        private String accessKey;

        @NotBlank(message = "ons consumer集群配置的secretKey不能为空")
        private String secretKey;

        //消息处理类集合
        @NotEmpty(message = "ons consumer的消息处理类集合不能为空")
        @Valid
        private List<MessageProces> supportMessageProcess;

        public String getConsumerId() {
            return consumerId;
        }

        public void setConsumerId(String consumerId) {
            this.consumerId = consumerId;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public List<MessageProces> getSupportMessageProcess() {
            return supportMessageProcess;
        }

        public void setSupportMessageProcess(List<MessageProces> supportMessageProcess) {
            this.supportMessageProcess = supportMessageProcess;
        }
    }

    /**
     * 消息处理类属性
     */
    public static class MessageProces {

        @NotBlank(message = "ons消费者集群配置中的消息处理类的topicTag不能为空")
        private String topicTag;

        @NotBlank(message = "ons消费者集群配置中的消息处理类的beanName不能为空")
        private String beanName;

        public String getTopicTag() {
            return topicTag;
        }

        public void setTopicTag(String topicTag) {
            this.topicTag = topicTag;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<ConsumerConfigProperties> getConsumerConfigs() {
        return consumerConfigs;
    }

    public void setConsumerConfigs(List<ConsumerConfigProperties> consumerConfigs) {
        this.consumerConfigs = consumerConfigs;
    }
}
