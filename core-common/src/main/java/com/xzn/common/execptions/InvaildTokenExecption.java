package com.xzn.common.execptions;

public class InvaildTokenExecption extends BusinessException {

    public InvaildTokenExecption(String xCode, String message) {
        super(xCode, message);
    }
}
