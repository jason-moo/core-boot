package com.xzn.fegin;

import lombok.Data;

@Data
public class ErrorMessageInfo {

    private String timestamp;

    private int status;

    private String error;

    private String message;

    private String path;
}
