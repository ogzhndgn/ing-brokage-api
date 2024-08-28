package org.thepoet.brokage.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ApiException extends RuntimeException {
    private final int httpStatus;
    private final ErrorCode errorCode;
    private final String body;
    private final Map<String, String> details;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.name());
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode;
        this.body = "";
        this.details = Map.of();
    }

    public ApiException(int httpStatus, ErrorCode errorCode) {
        super(errorCode.name());
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.body = "";
        this.details = Map.of();
    }

    public ApiException(int httpStatus, String body) {
        super(body);
        this.httpStatus = httpStatus;
        this.body = body;
        this.errorCode = ErrorCode.SYSTEM_ERROR;
        this.details = Map.of();
    }

    public ApiException(String body) {
        this.httpStatus = 400;
        this.body = body;
        this.errorCode = ErrorCode.SYSTEM_ERROR;
        this.details = Map.of();
    }

    public ApiException(ErrorCode errorCode, Map<String, String> details) {
        super(errorCode.name());
        this.httpStatus = errorCode.getHttpStatus();
        this.errorCode = errorCode;
        this.body = "";
        this.details = details;
    }

}
