package com.xzn.core.context;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.xzn.common.execptions.ExceptionFactory;
import com.xzn.common.utils.MapUtils;
import com.xzn.session.XSession;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Jintao on 2015/5/21.
 * <p/>
 * This class should be initialized in interceptor
 */
public class XContext {

    protected static final ThreadLocal<XContext> THREAD_LOCAL = new ThreadLocal<XContext>() {
        @Override
        protected XContext initialValue() {
            return new XContext();
        }
    };

    protected HttpServletRequest request;

    private JSONObject postJsonBody;

    private String postJsonBodyStr;

    private Map<String, String> requestParamMap;

    /**
     * 是否跳过签名
     */
    private boolean skipCheckSignature = false;

    /**
     * 请求开始时间
     */
    private long requestStartTime;

    /**
     * xcontext是否初始化
     */
    private boolean isInited = false;

    /**
     * 请求开源，openApi，wemall等
     */
    private String source;

    /**
     * 用户访问真实ip地址
     */
    private String realIp;

    private Object handler;

    /**
     * 返回结果的str字符串
     */
    private String resultJsonStr;

    private XSession xSession = new XSession();

    protected XContext() {

    }

    /**
     * 获取当前Context
     *
     * @return
     */
    public static XContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public XSession getSession() {
        return this.xSession;
    }

    public void setSession(XSession xSession) {
        if (xSession != null) {
            this.xSession = xSession;
        }
    }

    public String getServletPath() {
        return this.request.getServletPath();
    }

    /**
     * 在Interceptor中使用，初始化参数
     */
    public void init(HttpServletRequest request, Object handler) {
        this.request = request;
        this.setRequestStartTime(System.currentTimeMillis());
        this.isInited = true;
        this.handler = handler;
        this.realIp = request.getHeader("x-real-ip");
        if (isPostRequest()) {
            this.postJsonBody = parse(request);
        }
    }

    public boolean isInited() {
        return isInited;
    }

    /**
     * 获取请求对应的URL
     */
    public String getServiceUrl() {
        String queryString = request.getQueryString();
        if (StringUtils.isNotEmpty(queryString)) {
            return request.getRequestURI() + "?" + queryString;
        } else {
            return request.getRequestURI();
        }
    }

    /**
     * 获取Request中所有参数的HashCode
     *
     * @return HashCode
     */
    public int getRequestHashCode() {
        return request.hashCode();
    }

    /**
     * 将参数序列化成字符串
     *
     * @return
     */
    public String getParamsString() {
        Map<String, String> paramMap = getParameterMap();
        if (CollectionUtils.isEmpty(paramMap)) {
            return "{}";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("hashcode=").append(getRequestHashCode());
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            /** 过滤upYun开头的参数 */
            if (key.startsWith("upYun")) {
                continue;
            }
            if (key.contains("password")) {
                stringBuilder.append(',').append(key).append('=').append("******");
            } else {
                stringBuilder.append(',').append(key).append('=').append(entry.getValue());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 获取参数列表
     *
     * @return
     */
    public Map<String, String> getParameterMap() {
        if (requestParamMap == null) {
            if (isPostRequest()) {
                if (postJsonBody == null) {
                    return Collections.emptyMap();
                }
                requestParamMap = MapUtils.convertValueToString(this.postJsonBody);
            } else {
                requestParamMap = MapUtils.convertValueToString(request.getParameterMap());
            }
        }
        return requestParamMap;

    }

    public long getRequestStartTime() {
        return requestStartTime;
    }

    public void setRequestStartTime(long requestStartTime) {
        this.requestStartTime = requestStartTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRealIp() {
        return realIp;
    }

    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }

    /**
     * 获取参数
     *
     * @return
     */
    public String getParameter(String key) {
        return getParameterMap().get(key);
    }

    /**
     * Method 为POST时候以JSON方式传递参数
     *
     * @return
     */
    public String getPostJsonBodyStr() {
        if (StringUtils.isEmpty(postJsonBodyStr)) {
            postJsonBodyStr = "{}";
        }

        return postJsonBodyStr;
    }

    /**
     * 判断请求类型是否为Post
     *
     * @return 是否为Post请求
     */
    public boolean isPostRequest() {
        if (request == null) {
            throw ExceptionFactory.create("F_WEBKITS_COMMON_1006");
        }

        boolean isPost = request.getMethod().equals(HttpMethod.POST.name()) || request.getMethod()
                .equals(HttpMethod.PUT.name());
        return isPost && isJsonRequest();
    }

    /**
     * 判断请求类型是否为Json
     *
     * @return 是否为Json请求
     */
    public boolean isJsonRequest() {
        if (request == null) {
            throw ExceptionFactory.create("F_WEBKITS_COMMON_1006");
        }

        String contentType = request.getContentType();
        if (contentType.contains("application/json") || contentType.contains("text/json")) {
            return true;
        }
        return false;
    }

    /**
     * 将request中inputStream 解析为JSONObject
     *
     * @param request
     * @return 解析RequestBody中的Json字符串
     */
    private JSONObject parse(HttpServletRequest request) {
        try {
            postJsonBodyStr = IOUtils.toString(request.getInputStream(), "UTF-8");
            //subString 去掉"data="前缀
            if (postJsonBodyStr.startsWith("{\"data\":")) {
                postJsonBodyStr = (String) JSON.parseObject(postJsonBodyStr).get("data");
                return JSON.parseObject(postJsonBodyStr, Feature.SupportArrayToBean);
            }
            return JSON.parseObject(postJsonBodyStr, Feature.SupportArrayToBean);
        } catch (Exception ex) {
            throw ExceptionFactory.create("F_WEBKITS_COMMON_1007", request.getRequestURI(), postJsonBodyStr,
                    ex.getMessage());
        }
    }

    public String getHeader(String name) {
        return request.getHeader(name);
    }


    public <T extends Annotation> T getMethodAnnotation(Class<T> clz) {
        /* 跳过默认servlet解析器 */
        if (!(handler instanceof HandlerMethod)) {
            return null;
        }
        return ((HandlerMethod) handler).getMethodAnnotation(clz);
    }

    public String getResultJsonStr() {
        return resultJsonStr;
    }

    public void setResultJsonStr(String resultJsonStr) {
        this.resultJsonStr = resultJsonStr;
    }

    /**
     * 获取返回值的jsonStr，会根据输入的size限制获取长度
     *
     * @param size
     * @return
     */
    public String getResultJsonStrWithLimitSize(Integer size) {
        if (size < 0 || size > 10000) {
            return "";
        }
        String jsonStr = getResultJsonStr();
        if (StringUtils.isEmpty(jsonStr)) {
            return "";
        }
        if (resultJsonStr.length() <= size) {
            return jsonStr;
        } else {
            return jsonStr.substring(0, size);
        }
    }

    /**
     * 清理当前线程中的数据
     */
    public static void clear() {
        THREAD_LOCAL.remove();
    }

}