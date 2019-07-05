package com.xzn.uploader.autoconfig;

import com.xzn.uploader.file.FileUploader;
import com.xzn.uploader.file.FtpUploaderImpl;
import com.xzn.uploader.file.OssUploaderImpl;
import com.xzn.uploader.prop.FtpProperties;
import com.xzn.uploader.prop.OssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ruancl@xkeshi.com
 * @date 2017/10/17.
 */
@Configuration
public class UploaderAutoConfig {

    @EnableConfigurationProperties(OssProperties.class)
    @ConditionalOnProperty(prefix = "xkeshi.uploader.oss", value = "enable", havingValue = "true")
    @Configuration
    public static class OosAutoConfig {
        @Bean
        public FileUploader oosUploader(OssProperties ossProperties) {
            return new OssUploaderImpl(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                    ossProperties.getBucketName(),
                    ossProperties.getProjectName());
        }
    }

    @EnableConfigurationProperties(FtpProperties.class)
    @ConditionalOnProperty(prefix = "xkeshi.uploader.ftp", value = "enable", havingValue = "true")
    @Configuration
    public static class FtpAutoConfig {
        @Bean
        public FileUploader ftpUploader(FtpProperties ftpProperties) {
            return new FtpUploaderImpl(ftpProperties.getHost(),
                    ftpProperties.getPort(),
                    ftpProperties.getUser(),
                    ftpProperties.getPassword(),
                    ftpProperties.getRootDir());
        }
    }


}
