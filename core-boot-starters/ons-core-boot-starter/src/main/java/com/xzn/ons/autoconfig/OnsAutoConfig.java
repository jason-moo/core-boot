package com.xzn.ons.autoconfig;


import com.xzn.ons.ConsumerConfig;
import com.xzn.ons.MessageHandlerFactory;
import com.xzn.ons.MessageProcess;
import com.xzn.ons.ONSProducerServiceImpl;
import com.xzn.ons.prop.OnsConsumerProperties;
import com.xzn.ons.prop.OnsProducerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * ons自动配置
 *
 * @author guoqw
 * @since 2017-05-10 09:07
 */
@Configuration
public class OnsAutoConfig {

    private static Logger logger = LoggerFactory.getLogger(OnsAutoConfig.class);

    @Configuration
    @ConditionalOnProperty(prefix = "xkeshi.ons.producer", value = "enable", havingValue = "true")
    @EnableConfigurationProperties(value = OnsProducerProperties.class)
    public static class OnsProducerAutoConfig {

        private final OnsProducerProperties onsProducerProperties;

        public OnsProducerAutoConfig(OnsProducerProperties onsProducerProperties) {
            this.onsProducerProperties = onsProducerProperties;
        }

        @Bean(name = "onsProducerService", initMethod = "init")
        public ONSProducerServiceImpl onsProducerService() {
            ONSProducerServiceImpl onsProducerService = new ONSProducerServiceImpl();
            Properties properties = new Properties();
            properties.setProperty("ProducerId", onsProducerProperties.getProducerId());
            properties.setProperty("AccessKey", onsProducerProperties.getAccessKey());
            properties.setProperty("SecretKey", onsProducerProperties.getSecretKey());
            onsProducerService.setProperties(properties);
            return onsProducerService;
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "xkeshi.ons.consumer", value = "enable", havingValue = "true")
    @EnableConfigurationProperties(value = OnsConsumerProperties.class)
    public static class OnsConsumerAutoConfig {

        private final OnsConsumerProperties onsConsumerProperties;

        private final ConfigurableListableBeanFactory beanFactory;

        public OnsConsumerAutoConfig(OnsConsumerProperties onsConsumerProperties,
                                     ApplicationContext applicationContext) {
            this.onsConsumerProperties = onsConsumerProperties;

            if (applicationContext instanceof AbstractApplicationContext) {
                this.beanFactory = ((AbstractApplicationContext) applicationContext).getBeanFactory();
            } else {
                throw new IllegalStateException("ons消费者启动失败.请检查配置");
            }
        }

        @PostConstruct
        public void init() {
            logger.info("开始ons consumer配置...");
            MessageHandlerFactory messageHandlerFactory = new MessageHandlerFactory();
            List<ConsumerConfig> consumerConfigs = onsConsumerProperties.getConsumerConfigs()
                    .stream()
                    .map(consumerConfigProperties -> {
                        ConsumerConfig consumerConfig = new ConsumerConfig();
                        consumerConfig.setProperties(initConsumerProp(consumerConfigProperties));
                        Map<String, MessageProcess> supportMessageProcess = consumerConfigProperties.getSupportMessageProcess()
                                .stream()
                                .collect(Collectors.toMap(OnsConsumerProperties.MessageProces::getTopicTag,
                                        messageProces ->
                                                beanFactory.getBean(messageProces.getBeanName(), MessageProcess.class),
                                        (a, b) -> {
                                            logger.warn("重复配置的ons消息处理类=>", b.getClass().getName());
                                            return b;
                                        }));
                        consumerConfig.setSupportMessageProcess(supportMessageProcess);
                        return consumerConfig;
                    })
                    .collect(Collectors.toList());
            messageHandlerFactory.setConsumerConfigs(consumerConfigs);
            messageHandlerFactory.init();
            logger.info("ons consumer配置完成.");
        }

        private Properties initConsumerProp(OnsConsumerProperties.ConsumerConfigProperties consumerConfigProperties) {
            Properties properties = new Properties();
            properties.setProperty("ConsumerId", consumerConfigProperties.getConsumerId());
            properties.setProperty("AccessKey", consumerConfigProperties.getAccessKey());
            properties.setProperty("SecretKey", consumerConfigProperties.getSecretKey());
            return properties;
        }
    }
}
