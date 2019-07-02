package com.xzn.common.ensure;

import com.xzn.common.ensure.extensions.*;

import java.util.Collection;

/**
 * Created by Jintao on 2015/6/8.
 */
public class Ensure {

    public static EnsureParamObjectExtension that(Object tObject) {
        return new EnsureParamObjectExtension(tObject);
    }

    public static EnsureParamBooleanExtension that(boolean tObject) {
        return new EnsureParamBooleanExtension(tObject);
    }

    public static <T extends Collection> EnsureParamCollectionExtension that(T tObject) {
        return new EnsureParamCollectionExtension(tObject);
    }

    public static <T extends Boolean> EnsureParamBooleanExtension that(T tObject) {
        return new EnsureParamBooleanExtension(tObject);
    }

    public static <T extends Number> EnsureParamNumberExtension that(T tObject) {
        return new EnsureParamNumberExtension(tObject);
    }

    public static <T extends Enum> EnsureParamEnumExtension that(T tObject) {
        return new EnsureParamEnumExtension(tObject);
    }

    public static EnsureParamStringExtension that(String tObject) {
        return new EnsureParamStringExtension(tObject);
    }

}
