package cz.cvut.moviemate.recommendationservice.exception.handler;

import cz.cvut.moviemate.commonlib.error.handler.ValidationExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomExceptionHandler extends ValidationExceptionHandler {
}
