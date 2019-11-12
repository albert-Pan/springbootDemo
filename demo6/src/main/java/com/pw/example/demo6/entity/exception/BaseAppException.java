package com.pw.example.demo6.entity.exception;


import com.pw.example.demo6.entity.enums.ErrorTypeEnum;

public class BaseAppException extends RuntimeException {
    private int code;
    private String desc;
    private String type;

    public BaseAppException() {
    }

    public BaseAppException(int code) {
        this(code, (String)null, ErrorTypeEnum.APPLICATION.getType());
    }

    public BaseAppException(int code, String desc) {
        this(code, desc, ErrorTypeEnum.APPLICATION.getType());
    }

    public BaseAppException(int code, String desc, String type) {
        super(code + ":" + desc);
        this.code = code;
        this.desc = desc;
        this.type = type;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getType() {
        return this.type;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof BaseAppException)) {
            return false;
        } else {
            BaseAppException other = (BaseAppException)o;
            if(!other.canEqual(this)) {
                return false;
            } else if(this.getCode() != other.getCode()) {
                return false;
            } else {
                Object this$desc = this.getDesc();
                Object other$desc = other.getDesc();
                if(this$desc == null) {
                    if(other$desc != null) {
                        return false;
                    }
                } else if(!this$desc.equals(other$desc)) {
                    return false;
                }

                Object this$type = this.getType();
                Object other$type = other.getType();
                if(this$type == null) {
                    if(other$type != null) {
                        return false;
                    }
                } else if(!this$type.equals(other$type)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof BaseAppException;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = 1;
         result = result * 59 + this.getCode();
        Object desc = this.getDesc();
        result = result * 59 + (desc == null?43:desc.hashCode());
        Object type = this.getType();
        result = result * 59 + (type == null?43:type.hashCode());
        return result;
    }

    public String toString() {
        return "BaseAppException(code=" + this.getCode() + ", desc=" + this.getDesc() + ", type=" + this.getType() + ")";
    }
}
