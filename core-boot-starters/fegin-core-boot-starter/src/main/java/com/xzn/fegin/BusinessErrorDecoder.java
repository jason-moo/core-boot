package com.xzn.fegin;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class BusinessErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        Exception exception = null;
        try {
            ErrorMessageInfo messageInfo = JSON.parseObject(Util.toString(response.body().asReader()),ErrorMessageInfo.class);
            // 这里只封装4开头的请求异常
            exception = new HystrixBadRequestException(messageInfo.getMessage(), exception);
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return exception;
    }

}