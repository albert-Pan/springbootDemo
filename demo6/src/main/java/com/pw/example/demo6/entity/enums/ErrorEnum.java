package com.pw.example.demo6.entity.enums;

/**
 * @Classname ErrorEnum
 * @Description TODO
 * @Date 2019/10/9 0009 下午 6:21
 * @Created by albert
 */
public enum  ErrorEnum {
    /**
     * 系统错误
     */
    SYS_INVLIAD_REQUEST(6000, " 参数错误"),
    SYS_EXCEPTION_ERROR(6001, "请求格式异常"),
    DB_ERROR(6002, "数据库异常");

    /**
     *  错误code
     */
    private int code;

    /**
     * 错误标签
     */
    private String desc;

    /**
     *  错误类型
     */
    private String type;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ErrorEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
