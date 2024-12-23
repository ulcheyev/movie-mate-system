package cz.cvut.moviemate.commonlib.exception;

public class InvalidArgumentException extends MovieMateBaseException {

    public InvalidArgumentException(String message) {
        super(message, "ARGUMENT_VALIDATION_ERROR");
    }
}
