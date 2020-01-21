package com.xzn.core.vo;

import lombok.Data;

@Data
public class Result extends BaseResponse {

    public Result() {

    }

    public Result(String code, String description) {
        super.setCode(code);
        super.setDescription(description);
    }

    public Result(String code, String description, Object result) {
        super.setCode(code);
        super.setDescription(description);
        this.result = result;
    }

    public Result(Object result) {
        this.result = result;
    }

    private Object result;

    public static Result success() {
        return new Result("200", "请求成功");
    }

    public static Result success(Object o) {
        return new Result("200", "请求成功", o);
    }

}
