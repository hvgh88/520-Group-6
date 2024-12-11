package com.umass.hangout.user_registeration.constants;

import org.apache.tomcat.util.bcel.Const;

import java.util.Arrays;
import java.util.Optional;

public enum ResponseCodes {
    USER_REGISTER_SUCCESS(10001, Constants.USER_REGISTER_SUCCESS),
    USER_ALREADY_EXISTS(10002, Constants.USER_ALREADY_EXISTS),
    USER_DOES_NOT_EXIST(10003, Constants.USER_NOT_FOUND),
    USER_LOGIN_SUCCESS(10004, Constants.LOGIN_SUCCESS),
    USER_LOGIN_FAILURE_PASSWORD(10005, Constants.LOGIN_FAILED_PASSWORD),
    UP_LOOKUP_SUCCESS(10006, Constants.UP_LOOKUP_SUCCESS),
    UP_LOOKUP_FAIL(10007, Constants.UP_LOOKUP_FAIL),
    UP_DOES_NOT_EXIST(10008, Constants.UP_DOES_NOT_EXIST),
    UP_CREATE_SUCCESS(10009, Constants.UP_CREATE_SUCCESS),
    UP_UPDATE_SUCCESS(10010, Constants.UP_UPDATE_SUCCESS),
    UP_UPDATE_FAIL(10011, Constants.UP_UPDATE_FAIL),
    INVALID_INPUT(10012, Constants.INPUT_VALIDATION_FAILED),
    GENERIC_ERROR(10013, Constants.GENERIC_ERROR);

    private int value;
    private String message;

    ResponseCodes(int value, String message) {
        this.value = value;
        this.message = message;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public static Optional<ResponseCodes> valueOf(int value) {
        return Arrays.stream(values())
                .filter(legNo -> legNo.value == value)
                .findFirst();
    }
}
