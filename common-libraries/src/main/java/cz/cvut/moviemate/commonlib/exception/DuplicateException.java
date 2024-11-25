package cz.cvut.moviemate.commonlib.exception;

public class DuplicateException extends MovieMateBaseException {

    public DuplicateException(String message) {
        super(message, "DUPLICATE_ERROR");
    }
}