package com.deepak.androidflickr.model;

public class ErrorD {
    private String message;
    private int errorCode;

    public ErrorD(String message, int error_code){
        this.message = message;
        this.errorCode = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
