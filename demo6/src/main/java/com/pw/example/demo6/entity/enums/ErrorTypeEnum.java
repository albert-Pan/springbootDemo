package com.pw.example.demo6.entity.enums;

public enum ErrorTypeEnum {
    SYSTEM(9000, "系统错误", "SYS"),
    APPLICATION(9001, "应用错误", "APC"),
    BUSINESS(9002, "业务错误", "BIZ");

    private int code;
    private String label;
    private String type;

    private ErrorTypeEnum(int code, String label, String type) {
        this.code = code;
        this.label = label;
        this.type = type;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}