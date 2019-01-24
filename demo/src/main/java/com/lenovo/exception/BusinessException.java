package com.lenovo.exception;

public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public BusinessException(String msg, int code, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }


    public int getCode() {
        return code;
    }
}

