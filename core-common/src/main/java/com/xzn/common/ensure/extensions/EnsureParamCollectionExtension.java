package com.xzn.common.ensure.extensions;


import com.xzn.common.execptions.ExceptionFactory;

import java.util.Collection;


public class EnsureParamCollectionExtension extends EnsureParamObjectExtension {

    private Collection collection;

    public EnsureParamCollectionExtension(Collection collection) {
        super(collection);
        this.collection = collection;
    }

    public EnsureParamCollectionExtension isNotEmpty(String errorCode) {
        if (collection != null && collection.size() > 0) {
            return this;
        } else {
            throw ExceptionFactory.create(errorCode);
        }
    }

    @Override
    public EnsureParamCollectionExtension isNotNull(String errorCode) {
        if (collection == null) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamCollectionExtension isEqualTo(Collection obj, String errorCode) {
        if (!(collection == null ? obj == null : collection.equals(obj))) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamCollectionExtension isNotEqualTo(Collection obj, String errorCode) {
        if (collection == null ? obj == null : collection.equals(obj)) {
            throw ExceptionFactory.create(errorCode);
        }
        return this;
    }
}
