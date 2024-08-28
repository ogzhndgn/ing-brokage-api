package org.thepoet.brokage.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.thepoet.brokage.i18n.MessageTranslator;
import org.thepoet.brokage.model.error.ErrorDetail;
import org.thepoet.brokage.model.error.ErrorResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class BrokageAdminExceptionHandler extends ResponseEntityExceptionHandler {

    private final static String ERROR_PREFIX = "error.description.";

    private final MessageTranslator messageTranslator;

    @ExceptionHandler(value = {ApiException.class})
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ae, WebRequest request) {
        log.error(" ApiException occurred: ", ae);
        ErrorDetail errorDetail = ErrorDetail.builder()
                .errorCode(ae.getErrorCode())
                .message(messageTranslator.getMessage(ERROR_PREFIX + ae.getErrorCode(), ae.getDetails()))
                .details(ae.getDetails())
                .build();
        List<ErrorDetail> errorDetailList = new ArrayList<>();
        errorDetailList.add(errorDetail);
        ErrorResponse response = new ErrorResponse();
        response.setErrors(errorDetailList);
        return ResponseEntity.status(ae.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception e, WebRequest request) {
        log.error("Unhandled Exception Occurred: ", e);
        ErrorCode errorCode = ErrorCode.SYSTEM_ERROR;
        ErrorDetail errorDetail = ErrorDetail.builder()
                .errorCode(errorCode)
                .message(messageTranslator.getMessage(ERROR_PREFIX + errorCode.name()))
                .build();
        ErrorResponse response = new ErrorResponse();
        List<ErrorDetail> errorDetailList = new ArrayList<>();
        errorDetailList.add(errorDetail);
        response.setErrors(errorDetailList);
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(Exception e, WebRequest request) {
        log.error("AccessDeniedException occurred: ", e);
        ErrorDetail errorDetail = ErrorDetail.builder()
                .errorCode(ErrorCode.INSUFFICIENT_PERMISSION)
                .message("Do not have permission to see this page")
                .build();
        ErrorResponse response = new ErrorResponse();
        List<ErrorDetail> errorDetailList = new ArrayList<>();
        errorDetailList.add(errorDetail);
        response.setErrors(errorDetailList);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(response);
    }

    @Override
    @NonNull
    protected ResponseEntity<Object> handleHttpMessageNotReadable(@NonNull HttpMessageNotReadableException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        log.error("Message Not HttpMessageNotReadableException: ", ex);

        String fieldName = getFieldNameFromHttpMessageNotReadableException(ex);
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST_BODY;
        ErrorDetail errorDetail = ErrorDetail.builder()
                .errorCode(errorCode)
                .message(messageTranslator.getMessage(ERROR_PREFIX + errorCode.name()))
                .parameter(fieldName)
                .build();
        List<ErrorDetail> errorDetailList = new ArrayList<>();
        errorDetailList.add(errorDetail);
        ErrorResponse response = new ErrorResponse();
        response.setErrors(errorDetailList);
        return ResponseEntity.status(status)
                .body(response);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        log.error("ConstraintViolationException occurred: ", ex);

        final ArrayList<ErrorDetail> errorDetails = new ArrayList<>();
        ex.getConstraintViolations()
                .forEach(constraintViolation -> {
                    ErrorDetail errorDetail = new ErrorDetail();
                    errorDetail.setErrorCode(ErrorCode.toEnum(constraintViolation.getMessage()));
                    errorDetail.setMessage(messageTranslator.getMessage(ERROR_PREFIX + constraintViolation.getMessage()));
                    errorDetails.add(errorDetail);
                });
        ErrorResponse response = new ErrorResponse();
        response.setErrors(errorDetails);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                               @NonNull HttpHeaders headers,
                                                               @NonNull HttpStatusCode status,
                                                               @NonNull WebRequest request) {
        log.error("MethodArgumentNotValidException occurred: ", ex);

        return getErrorResponseFromBindException(ex);
    }

    private ResponseEntity<Object> getErrorResponseFromBindException(BindException ex) {
        List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
        List<ErrorDetail> errorDetailList = errorList.stream().map(error -> ErrorDetail.builder()
                        .errorCode(ErrorCode.toEnum(error.getDefaultMessage()))
                        .message(messageTranslator.getMessage(ERROR_PREFIX + error.getDefaultMessage()))
                        .parameter(getFieldFromObjectError(error))
                        .build())
                .collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrors(errorDetailList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private String getFieldFromObjectError(ObjectError error) {
        return Optional.of(error)
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .map(FieldError::getField)
                .orElse(null);
    }

    private String getFieldNameFromHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        try {
            MismatchedInputException mismatchedInputException = (MismatchedInputException) ex.getCause();
            return mismatchedInputException.getPath()
                    .get(0)
                    .getFieldName();
        } catch (Exception e) {
            return "";
        }
    }
}