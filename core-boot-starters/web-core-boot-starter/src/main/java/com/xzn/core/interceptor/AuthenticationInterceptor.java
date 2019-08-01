package com.xzn.core.interceptor;

import com.xzn.annotations.LoginRequired;
import com.xzn.common.execptions.ExceptionFactory;
import com.xzn.core.context.XContext;
import com.xzn.core.jwt.CheckResult;
import com.xzn.core.jwt.SystemConstant;
import com.xzn.core.utils.JwtUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor extends XInterceptor {

    public final static String ACCESS_TOKEN = "accessToken";

    @Override
    protected boolean internalPreHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);
        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 判断是否存在令牌信息，如果存在，则允许登录
            String authHeader = request.getHeader(ACCESS_TOKEN);
            if (StringUtils.isEmpty(authHeader)) {
                throw ExceptionFactory.create("F_WEBKITS_SECURITY_1002");
            } else {
                //验证JWT的签名，返回CheckResult对象
                CheckResult checkResult = JwtUtils.validateJWT(authHeader);
                if (checkResult.isSuccess()) {
                    XContext.getCurrentContext().getSession().setUserId(checkResult.getClaims().getId());
                    putInfoToSession();
                    return true;
                } else {
                    switch (checkResult.getErrCode()) {
                        // 签名验证不通过
                        case SystemConstant.JWT_ERRCODE_FAIL:
                            throw ExceptionFactory.create("F_WEBKITS_SECURITY_1003");
                        // 签名过期，返回过期提示码
                        case SystemConstant.JWT_ERRCODE_EXPIRE:
                            throw ExceptionFactory.create("F_WEBKITS_SECURITY_1001");
                        default:
                            break;
                    }
                    return false;
                }
            }

        } else {// 不需要登录可请求
            return true;
        }
    }

    // 自定义实现设置用户信息到XSession中
    protected void putInfoToSession(){

    }

}
