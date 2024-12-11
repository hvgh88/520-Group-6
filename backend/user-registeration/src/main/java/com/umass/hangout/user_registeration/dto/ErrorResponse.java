package com.umass.hangout.user_registeration.dto;

import com.umass.hangout.user_registeration.constants.Constants;
import com.umass.hangout.user_registeration.constants.ResponseCodes;

public class ErrorResponse {
    private int code;
    private String message;

    public ErrorResponse() {
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(ResponseCodes responseCode) {
        this.code = responseCode.getValue();
        this.message = responseCode.getMessage();
    }


    public int getCode() {
        return code;
    }

    public void setCodeAndMessage(ResponseCodes responseCode) {
        this.code = responseCode.getValue();
        this.message = responseCode.getMessage();
    }

    public String getMessage() {
        return message;
    }
}
