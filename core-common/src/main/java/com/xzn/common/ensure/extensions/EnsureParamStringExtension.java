package com.xzn.common.ensure.extensions;


import com.xzn.common.ensure.EnsureParam;
import com.xzn.common.execptions.ExceptionFactory;
import org.apache.commons.lang3.StringUtils;

public class EnsureParamStringExtension extends EnsureParam<Object> {
    private String string;

    public EnsureParamStringExtension(String string) {
        super(string);
        this.string = string;
    }

    public EnsureParamStringExtension isNotNull(String errorCode) {
        if (string == null) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamStringExtension isNotEmpty(String errorCode) {
        if (StringUtils.isEmpty(string)) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamStringExtension isNotBlank(String errorCode) {
        if (StringUtils.isBlank(string)) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamStringExtension isEqualTo(String comparedString, String errorCode) {
        if (!StringUtils.equals(string, comparedString)) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamStringExtension isNotEqualTo(String comparedString, String errorCode) {
        if (StringUtils.equals(string, comparedString)) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

}
