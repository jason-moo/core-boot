package com.xzn.core.ensure.extensions;

import com.xzn.core.ensure.EnsureParam;
import com.xzn.core.execptions.ExceptionFactory;

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
