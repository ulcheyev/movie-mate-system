package cz.cvut.moviemate.activityservice.exception.handler;

import cz.cvut.moviemate.commonlib.error.handler.ValidationExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomValidationHandler extends ValidationExceptionHandler {
}
