package cz.cvut.moviemate.userservice.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.cvut.moviemate.commonlib.error.handler.BaseAccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class AccessDeniedExceptionHandler extends BaseAccessDeniedHandler {
    public AccessDeniedExceptionHandler(ObjectMapper objectMapper) {
        super(objectMapper);
    }
}
