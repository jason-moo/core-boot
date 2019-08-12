package com.xzn.core.vo;

import lombok.Data;

@Data
public class Result<T> extends BaseResponse{

    public Result() {

    }

    public Result(String code, String description) {
        super.setCode(code);
        super.setDescription(description);
    }

    public Result(String code, String description, T result) {
        super.setCode(code);
        super.setDescription(description);
        this.result = result;
    }

    public Result(T result) {
        this.result = result;
    }

    private T result;

}
