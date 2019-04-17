package com.pw.example.demo4.model;
public enum  ResponseCode {
    SUCCESS(20000, "success"),
    LOGIN_FAIL(50001, "login fail"),
    USER_NOT_EXIST(50002, " user does not exist "),
    PASSWORD_ERROR(50003, "  Password error "),
    UNAUTH(50004, "  Not logged in "),
    /*    ILLEGAL_TOKEN(50008,"illegal token"),
        DUPLICATE_LOGIN(50012,"duplicate login"),
        TOKEN_EXPIRES(50014,"token expires"),*/
    UNAUTHORIZED(50015, " Insufficient authority "),
    SERVER_ERROR(50016, " server error "),
    BUSINESS_ERROR(50017, " business error "),
    UNKNOWN(50000, " unknown error ");
    private int code;
    private String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

