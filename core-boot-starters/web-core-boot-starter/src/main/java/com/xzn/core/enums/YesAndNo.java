package com.xzn.core.enums;

public enum YesAndNo {

    YES(1, "是"),

    NO(0, "否");

    private int code;

    private String des;

    YesAndNo(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
