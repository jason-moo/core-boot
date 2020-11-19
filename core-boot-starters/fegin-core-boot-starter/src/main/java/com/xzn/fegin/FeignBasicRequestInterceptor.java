package com.xzn.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import javax.servlet.http.HttpServletRequest;

public class FeignBasicRequestInterceptor implements RequestInterceptor {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token = httpServletRequest.getHeader("accessToken");
        if (!StringUtils.isEmpty(token)){
            requestTemplate.header("accessToken",httpServletRequest.getHeader("accessToken"));
        }else {
            requestTemplate.header("accessToken","asdasdwgrefwadwqefqfwqfwqf");
        }
    }

}

