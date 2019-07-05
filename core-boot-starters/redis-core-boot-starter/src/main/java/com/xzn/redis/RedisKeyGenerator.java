package com.xzn.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * 自定义的Redis键值生成器.
 * Created by lll@xkeshi.com on 17/4/13.
 */
public class RedisKeyGenerator implements KeyGenerator {

    private static final int NO_PARAM_KEY = 0;
    private static final int NULL_PARAM_KEY = 53;
    private static SerializerFeature[] features = {SerializerFeature.DisableCircularReferenceDetect};

    @Override
    public Object generate(Object target, Method method, Object... params) {
        if (params.length == 0) {
            return NO_PARAM_KEY;
        }
        String jsonStr = null;
        if (params.length == 1) {
            Object param = params[0];
            if (param == null) {
                return NULL_PARAM_KEY;
            }
            jsonStr = JSON.toJSONString(param, features);
        }
        if (jsonStr == null) {
            jsonStr = JSON.toJSONString(params, features);
        }
        jsonStr = jsonStr.replaceAll(":", "-");
        return jsonStr;
    }

}
