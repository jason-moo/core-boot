package com.xzn.common.ensure.extensions;


import com.xzn.common.ensure.EnsureParam;
import com.xzn.common.execptions.ExceptionFactory;

public class EnsureParamEnumExtension extends EnsureParam<Enum> {
    private Enum anEnum;

    public EnsureParamEnumExtension(Enum anEnum) {
        super(anEnum);
        this.anEnum = anEnum;
    }

    /**
     * Enum 相等
     *
     * @param comparedEnum
     * @param errorCode
     * @return
     */
    public EnsureParamEnumExtension isEqual(Enum comparedEnum, String errorCode) {
        if (anEnum != comparedEnum) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    /**
     * Enum 不相等
     *
     * @param comparedEnum
     * @param errorCode
     * @return
     */
    public EnsureParamEnumExtension isNotEqual(Enum comparedEnum, String errorCode) {
        if (anEnum == comparedEnum) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamEnumExtension isNotNull(String errorCode) {
        if (anEnum == null) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }
}
