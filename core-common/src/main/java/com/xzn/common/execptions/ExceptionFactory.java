package com.xzn.common.execptions;


import com.xzn.common.contexthelp.ApplicationContextHelper;

public class ExceptionFactory {

    private static ExceptionDefinitions exceptionDefinitions;

    public static BusinessException create(String errorCode, String... args) {
        return createWithResult(errorCode, null, args);
    }

    public static BusinessException createWithResult(String errorCode, Object result, String... args) {
        String exceptionPattern = getExceptionDefinitions().getExceptionMessage(errorCode);

        if (args.length > 0) {
            String errorMsg = String.format(exceptionPattern, args);
            return new BusinessException(errorCode, errorMsg, result);
        }
        return new BusinessException(errorCode, exceptionPattern, result);
    }

    private static ExceptionDefinitions getExceptionDefinitions() {
        if (exceptionDefinitions == null) {
            exceptionDefinitions = ApplicationContextHelper.getContext().getBean(ExceptionDefinitions.class);
        }
        return exceptionDefinitions;
    }

}
