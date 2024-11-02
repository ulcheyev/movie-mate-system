package cz.cvut.userservice.exception;

public class DuplicateException extends MovieMateBaseException {

    public DuplicateException(String message) {
        super(message, "DUPLICATE_ERROR");
    }
}
