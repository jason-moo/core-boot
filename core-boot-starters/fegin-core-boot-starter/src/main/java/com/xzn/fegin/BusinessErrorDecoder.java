package com.xzn.fegin;

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
            Util.toString(response.body().asReader());
            // 这里只封装4开头的请求异常
            if (400 <= response.status() && response.status() < 500){
                exception = new HystrixBadRequestException("request exception wrapper", exception);
            }else{
                log.error(exception.getMessage(), exception);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
        return exception;
    }

}