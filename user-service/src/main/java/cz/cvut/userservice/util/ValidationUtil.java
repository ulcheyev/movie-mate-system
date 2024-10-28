package cz.cvut.userservice.util;

import cz.cvut.userservice.exception.DuplicateException;
import cz.cvut.userservice.exception.NotFoundException;
import cz.cvut.userservice.model.AppUser;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ValidationUtil {

    public <T> void checkDuplicate(T value, Function<T, AppUser> findFunc, String fieldName) {
        try {
            findFunc.apply(value);
            throw new DuplicateException(String.format("User with %s %s already exists", fieldName, value));
        } catch (NotFoundException ignored) {
            // Duplicates not found
        }
    }
}
