package com.xzn.core.execptions;

import lombok.Data;

@Data
public class BusinessException extends XRuntimeException {

    private static final long serialVersionUID = 1469688255681816280L;

    public static final String DEFAULT_FAULT_CODE = "X0001";

    private String code;
    private String message;
    private Object result;

    public BusinessException(String message) {
        this(DEFAULT_FAULT_CODE, message);
    }

    public BusinessException(String xCode, String message) {
        this(xCode, message, new Throwable());
    }

    public BusinessException(String xCode, String message, Object result) {
        this(xCode, message, result, new Throwable());
    }

    public BusinessException(String xCode, String message, Object result, Throwable throwable) {
        this(xCode, message, result, throwable.getMessage(), throwable);
    }

    public BusinessException(String xCode, String message, String internalMessage) {
        this(xCode, message, internalMessage, null);
    }

    public BusinessException(String code, String message, Throwable throwable) {
        this(code, message, throwable.getMessage(), throwable);
    }

    public BusinessException(String xCode, String message, String internalMessage, Throwable throwable) {
        this(xCode, message, null, internalMessage, throwable);
    }

    public BusinessException(String xCode, String message, Object result, String internalMessage, Throwable throwable) {
        super("[" + xCode + "] - " + message + internalMessage, throwable);
        this.message = message;
        this.code = xCode;
        this.result = result;
    }

}
