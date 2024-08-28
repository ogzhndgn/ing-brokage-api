package org.thepoet.brokage.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    FATAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value()),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value()),
    MULTIPLE_ADMINS_FOUND(HttpStatus.CONFLICT.value()),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND.value()),
    INSUFFICIENT_PERMISSION(HttpStatus.FORBIDDEN.value()),
    INVALID_REQUEST_BODY(HttpStatus.BAD_REQUEST.value()),
    ASSET_NOT_FOUND(HttpStatus.NOT_FOUND.value()),
    INVALID_CUSTOMER_FOR_ASSET(HttpStatus.CONFLICT.value()),
    INVALID_ASSET_SIZE_FOR_ORDER(HttpStatus.BAD_REQUEST.value()),
    INVALID_BROKAGE_ADMIN_FOR_ORDER(HttpStatus.FORBIDDEN.value()),
    INVALID_ORDER_STATUS_FOR_CANCEL(HttpStatus.BAD_REQUEST.value()),
    INVALID_ORDER_STATUS_FOR_MATCH(HttpStatus.BAD_REQUEST.value()),
    INVALID_DIRECTION_ERROR(HttpStatus.BAD_REQUEST.value()),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND.value()),
    PARAMETER_REQUIRED(HttpStatus.BAD_REQUEST.value()),
    INVALID_LOGIN(HttpStatus.UNAUTHORIZED.value());
    private final int httpStatus;

    ErrorCode(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public static ErrorCode toEnum(String value) {
        try {
            return ErrorCode.valueOf(value);
        } catch (Exception e) {
            return ErrorCode.FATAL_ERROR;
        }
    }

    public int getHttpStatus() {
        return httpStatus;
    }


}
