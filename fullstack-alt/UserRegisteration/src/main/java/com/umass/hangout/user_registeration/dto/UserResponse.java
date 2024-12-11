package com.umass.hangout.user_registeration.dto;

import com.umass.hangout.user_registeration.dto.ErrorResponse;

public class UserResponse {
    private int httpResponseCode;
    private UserSuccessResponse successResponse;
    private ErrorResponse errorResponse;

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public UserSuccessResponse getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(UserSuccessResponse successResponse) {
        this.successResponse = successResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}

