package com.xzn.core.handler;

import com.xzn.common.execptions.BusinessException;
import com.xzn.common.execptions.ExceptionFactory;
import com.xzn.common.utils.ResponseUtils;
import com.xzn.core.vo.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice("com.core")
@ResponseBody
public class GlobalExceptionHandler {
  private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 500- 业务异常
   */
  @ExceptionHandler(BusinessException.class)
  public BaseResponse baseExceptionHandler(HttpServletResponse response, BusinessException ex) {
    logger.error(ex.getMessage(), ex);
    return new BaseResponse(ex.getCode(), ex.getMessage());
  }

  /**
   * 500- server error
   */
  @ExceptionHandler(Exception.class)
  public BaseResponse otherExceptionHandler(HttpServletResponse response, Exception ex) {
    logger.error(ex.getMessage(), ex);
    return ResponseUtils.getUnknownResult();
  }

  /**
   * 缺少请求参数- Bad Request
   */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public BaseResponse handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {
    logger.error(ex.getMessage(), ex);
    return new BaseResponse("F_WEBKITS_COMMON_1005", ex.getMessage());
  }

  /**
   * 参数解析失败- Bad Request
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public BaseResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
    logger.error(ex.getMessage(), ex);
    return new BaseResponse("F_WEBKITS_COMMON_1014", ex.getMessage());
  }

  /**
   * 参数验证失败 - Bad Request
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    logger.error(ex.getMessage(), ex);
    return new BaseResponse("F_WEBKITS_COMMON_1003",
        ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
  }

  /**
   * 参数绑定失败- Bad Request
   */
  @ExceptionHandler(BindException.class)
  public BaseResponse handleBindException(BindException ex) {
    logger.error(ex.getMessage(), ex);
    return new BaseResponse("F_WEBKITS_COMMON_1015",
        ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
  }

  /**
   * 参数类型不匹配
   */
  @ExceptionHandler(JSONException.class)
  public BaseResponse processArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
    return new BaseResponse("F_WEBKITS_COMMON_1002", ex.getMessage());
  }

  /**
   * 请求方法不匹配
   */
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public BaseResponse handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex) {
    Exception e = ExceptionFactory.create("F_WEBKITS_COMMON_1004", ex.getMethod());
    return new BaseResponse(((BusinessException) e).getCode(), e.getMessage());
  }

}
