package com.umass.hangout.user_registeration.dto;

import com.umass.hangout.user_registeration.dto.ErrorResponse;

public class UserProfileResponse {
    private int httpResponseCode;
    private UserProfileSuccessResponse successResponse;
    private ErrorResponse errorResponse;

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public UserProfileSuccessResponse getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(UserProfileSuccessResponse successResponse) {
        this.successResponse = successResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}

