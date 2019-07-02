package com.xzn.common.execptions;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


@Slf4j
public class ExceptionDefinitions {

    private Logger logger = LoggerFactory.getLogger(ExceptionDefinitions.class);

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    private Properties exceptionDefinitionProps;

    /**
     * 获取properties（缓存Properties）
     *
     * @return properties
     * @throws IOException
     */
    private Properties getDefinitions() throws IOException {

        if (exceptionDefinitionProps == null) {
            Resource[] resources = resourcePatternResolver.getResources("classpath*:/props/error.properties");
            exceptionDefinitionProps = new Properties();
            for (Resource resource : resources) {
                InputStream stream = resource.getInputStream();
                try {
                    Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                    try {
                        exceptionDefinitionProps.load(reader);
                    } finally {
                        reader.close();
                    }
                } finally {
                    stream.close();
                }
            }
        }
        return exceptionDefinitionProps;
    }

    /**
     * 根据错误代码获取异常描述信息
     *
     * @param errorCode 错误代码
     * @return 异常描述信息
     */
    public String getExceptionMessage(String errorCode) {

        final String cannotFoundErrorCodeMessagePattern = "系统错误[ErrorType = ERROR_MESSAGE_DEFINITION, ErrorCode=%s]";

        String message = StringUtils.EMPTY;
        try {
            message = (String) getDefinitions().get(errorCode);
        } catch (IOException e) {
            logger.error(String.format("Error message for [code=%s] is not defined", errorCode));
        }

        if (StringUtils.isEmpty(message)) {
            message = String.format(cannotFoundErrorCodeMessagePattern, errorCode);
        }

        return message;
    }
}
