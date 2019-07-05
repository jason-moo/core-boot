package com.xzn.uploader.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author ruancl@xkeshi.com
 * @date 2017/10/17.
 */
@ConfigurationProperties(prefix = "xkeshi.uploader.oss")
@Validated
public class OssProperties {

    private boolean enable = false;

    private String endPoint = "oss-cn-hangzhou.aliyuncs.com";

    @NotNull(message = "请填写访问id")
    private String accessKeyId;

    @NotNull(message = "请填写访问密码")
    private String accessKeySecret;

    private String bucketName = "test-xkeshi-oss";

    @NotNull(message = "请填写项目名")
    private String projectName;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
