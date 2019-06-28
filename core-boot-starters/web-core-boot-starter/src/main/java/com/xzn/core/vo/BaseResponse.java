package com.xzn.core.vo;

import lombok.Data;

@Data
public class BaseResponse {

    private String code = "200";

    private String description;

    public BaseResponse(String status, String description) {
        this.code = status;
        this.description = description;
    }

    public BaseResponse() {
    }

}
