package cz.cvut.moviemate.commonlib.error.handler;

import cz.cvut.moviemate.commonlib.error.ApiErrorMultipleResponses;
import cz.cvut.moviemate.commonlib.utils.ApiErrorFactory;
import org.springframework.context.MessageSourceResolvable;
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
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorMultipleResponses> handleArgumentNotValidException(
            MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = extractFieldErrors(ex);
        ApiErrorMultipleResponses errorResponse = ApiErrorFactory.createApiErrorResponses(
                HttpStatus.BAD_REQUEST,
                "ARGUMENT_FORMAT_ERROR",
                errors,
                new String[]{"Check the request payload for invalid fields."},
                ex.getCause(),
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorMultipleResponses> handleMethodNotValidException(
            HandlerMethodValidationException ex, WebRequest request) {
        Map<String, String> errors = extractGlobalErrors(ex);
        ApiErrorMultipleResponses errorResponse = ApiErrorFactory.createApiErrorResponses(
                HttpStatus.BAD_REQUEST,
                "METHOD_VALIDATION_ERROR",
                errors,
                new String[]{"Review method-level validation constraints."},
                ex.getCause(),
                request
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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