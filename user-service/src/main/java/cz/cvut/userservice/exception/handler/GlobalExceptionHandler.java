package cz.cvut.userservice.exception.handler;

import cz.cvut.userservice.dto.error.ApiErrorSingleResponse;
import cz.cvut.userservice.exception.MovieMateBaseException;
import cz.cvut.userservice.util.ApiErrorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RequiredArgsConstructor
@Order
public class GlobalExceptionHandler {
    private final ApiErrorFactory apiErrorFactory;

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ApiErrorSingleResponse> handleGenericException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiErrorSingleResponse errorResponse = apiErrorFactory.createApiErrorResponse(
                status,
                "INTERNAL_SERVER_ERROR",
                String.format("Internal error: %s", ex.getMessage()),
                "An unexpected error occurred. Please try again later.",
                request,
                ex.getCause(),
                new String[]{"Check the application logs for more details.", "Contact support if the issue persists."}
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(MovieMateBaseException.class)
    @ResponseBody
    public ResponseEntity<ApiErrorSingleResponse> handleAppException(MovieMateBaseException ex, WebRequest request) {
        HttpStatus status = HttpStatus.I_AM_A_TEAPOT;

        ApiErrorSingleResponse errorResponse = apiErrorFactory.createApiErrorResponse(
                status,
                ex.getErrorType(),
                String.format("Generic internal error: %s", ex.getMessage()),
                "Service unavailable due to generic internal error.",
                request,
                ex.getCause(),
                new String[]{"Check application logs for more details."}
        );

        return new ResponseEntity<>(errorResponse, status);
    }
}
