package cz.cvut.moviemate.commonlib.error.handler;

import cz.cvut.moviemate.commonlib.error.ApiErrorMultipleResponses;
import cz.cvut.moviemate.commonlib.error.ApiErrorSingleResponse;
import cz.cvut.moviemate.commonlib.exception.DuplicateException;
import cz.cvut.moviemate.commonlib.exception.InvalidTimestampException;
import cz.cvut.moviemate.commonlib.utils.ApiErrorFactory;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(-1)
public class ValidationExceptionHandler extends GlobalExceptionHandler{

    @ExceptionHandler(DuplicateException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorSingleResponse> handleDuplicateException(
            DuplicateException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        ApiErrorSingleResponse errorResponse = ApiErrorFactory.createApiErrorResponse(
                status,
                "DUPLICATE_ERROR",
                ex.getMessage(),
                "The request could not be completed due to a conflict with the current state of the resource.",
                request,
                ex.getCause(),
                new String[]{"Check for duplicate data and try again.", "Ensure that the resource is in a valid state."}
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(InvalidTimestampException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorSingleResponse> handleInvalidTimestampException(
            InvalidTimestampException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiErrorSingleResponse errorResponse = ApiErrorFactory.createApiErrorResponse(
                status,
                ex.getErrorType(),
                ex.getMessage(),
                "The timestamp format or date range must be wrong.",
                request,
                ex.getCause(),
                new String[]{"Check the timestamp format.", "Validate date range."}
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorMultipleResponses> handleArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, String> errors = extractFieldErrors(ex);
        ApiErrorMultipleResponses errorResponse = ApiErrorFactory.createApiErrorResponses(
                status,
                "ARGUMENT_FORMAT_ERROR",
                errors,
                new String[]{"Check the request payload for invalid fields."},
                ex.getCause(),
                request
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorMultipleResponses> handleMethodNotValidException(
            HandlerMethodValidationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, String> errors = extractGlobalErrors(ex);
        ApiErrorMultipleResponses errorResponse = ApiErrorFactory.createApiErrorResponses(
                status,
                "METHOD_VALIDATION_ERROR",
                errors,
                new String[]{"Review method-level validation constraints."},
                ex.getCause(),
                request
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    private Map<String, String> extractFieldErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }

    private Map<String, String> extractGlobalErrors(HandlerMethodValidationException ex) {
        AtomicInteger index = new AtomicInteger(1);
        return ex.getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> "error" + index.getAndIncrement(),
                        MessageSourceResolvable::getDefaultMessage));
    }
}