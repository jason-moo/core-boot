package com.xzn.ons.prop;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * ons producer 属性配置
 *
 */
@ConfigurationProperties(prefix = "xkeshi.ons.producer")
@Validated
public class OnsProducerProperties {

    /**
     * 是否开启ons producer配置
     */
    private boolean enable = false;

    @NotBlank(message = "ons producer的producerId不能为空")
    private String producerId;

    @NotBlank(message = "ons producer的accessKey不能为空")
    private String accessKey;

    @NotBlank(message = "ons producer的secretKey不能为空")
    private String secretKey;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
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
}
