package com.xzn.core.vo;

/**
 * Created by Ace on 2017/6/11.
 */
public class ObjectRestResponse<T> extends BaseResponse {

    T data;
    boolean rel = true;

    public boolean isRel() {
        return rel;
    }

    public void setRel(boolean rel) {
        this.rel = rel;
    }


    public ObjectRestResponse rel(boolean rel) {
        setRel(rel);
        return this;
    }


    public ObjectRestResponse data(T data) {
        setData(data);
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
