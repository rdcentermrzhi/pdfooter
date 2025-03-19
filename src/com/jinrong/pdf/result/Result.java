package com.jinrong.pdf.result;

import java.io.Serializable;

public class Result implements Serializable {
    private static final long serialVersionUID = -1690196660514874159L;

    protected boolean success = true;

    protected String code;

    protected String message;

    public Result copy(Result result) {
        if (result != null) {
            this.success = result.success;
            this.code = result.code;
            this.message = result.message;
        }
        return this;
    }

    public Result fail() {
        this.success = false;
        return this;
    }

    public Result fail(String code) {
        this.success = false;
        this.code = code;
        return this;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public boolean isFail() {
        return !this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        String nvDelimiter = "=";
        String fDelimiter = ",";
        return
                "success" + nvDelimiter + this.success + fDelimiter +
                        "code" + nvDelimiter + this.code + fDelimiter +
                        "message" + nvDelimiter + this.message;
    }
}
