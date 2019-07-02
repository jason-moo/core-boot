package com.xzn.common.ensure.extensions;


import com.xzn.common.ensure.EnsureParam;
import com.xzn.common.execptions.ExceptionFactory;

public class EnsureParamBooleanExtension extends EnsureParam<Boolean> {
    private Boolean condition;

    public EnsureParamBooleanExtension(Boolean condition) {
        super(condition);
        this.condition = condition;
    }

    public EnsureParamBooleanExtension isFalse(String errorCode) {
        if (condition) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamBooleanExtension isTrue(String errorCode) {
        if (!condition) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamBooleanExtension isNotNull(String errorCode) {
        if (condition == null) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

}
