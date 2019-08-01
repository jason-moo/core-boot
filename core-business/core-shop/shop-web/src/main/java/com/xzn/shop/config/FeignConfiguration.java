package com.xzn.shop.config;

import com.xzn.common.utils.StringUtils;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public FeignBasicRequestInterceptor feignBasicRequestInterceptor(){
        return new FeignBasicRequestInterceptor();
    }

    public static class FeignBasicRequestInterceptor implements RequestInterceptor{

        @Autowired
        private HttpServletRequest httpServletRequest;

        @Override
        public void apply(RequestTemplate requestTemplate) {
            String token = httpServletRequest.getHeader("accessToken");
            if (!StringUtils.isEmpty(token)){
                requestTemplate.header("token",httpServletRequest.getHeader("accessToken"));
            }
        }
    }

}
