package com.xzn.core.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Zhenwei on 7/23/15.
 */
public class HttpRequestUtils {

    @SuppressWarnings("rawtypes")
    public static Map<String, String> requestParamsMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            StringBuilder builder = new StringBuilder();
            int length = values.length;
            for (int i = 0; i < length; i++) {
                builder.append(values[i]);
                if (i != length - 1) {
                    builder.append(',');
                }
            }
            params.put(key, builder.toString());
        }
        return params;
    }

    /**
     * 获取cookie
     *
     * @param request
     * @param cookieName
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookieName, cookie.getName())) {
                return cookie;
            }
        }

        return null;
    }

}
