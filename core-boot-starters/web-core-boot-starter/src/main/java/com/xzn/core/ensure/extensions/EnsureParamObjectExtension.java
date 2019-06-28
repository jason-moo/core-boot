package com.xzn.core.ensure.extensions;


import com.xzn.core.ensure.EnsureParam;
import com.xzn.core.execptions.ExceptionFactory;

public class EnsureParamObjectExtension extends EnsureParam<Object> {
    private boolean isSatisfied;

    public EnsureParamObjectExtension(Object o) {
        super(o);
    }

    public EnsureParamObjectExtension isNotNull(String errorCode) {
        if (tObjct == null) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public <T> EnsureParamObjectExtension isEqualTo(T obj, String errorCode) {
        isSatisfied = (obj == tObjct) || (obj != null && tObjct != null && tObjct.equals(obj));

        if (!isSatisfied) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public <T> EnsureParamObjectExtension isNotEqualTo(T obj, String errorCode) {
        if (obj != tObjct) {
            if (obj != null) {
                isSatisfied = !obj.equals(tObjct);
            } else if (tObjct != null) {
                isSatisfied = !tObjct.equals(obj);
            } else {
                isSatisfied = false;
            }
        } else {
            isSatisfied = false;
        }

        if (!isSatisfied) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }
}
