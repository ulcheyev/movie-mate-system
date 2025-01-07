package cz.cvut.moviemate.commonlib.exception;

public class NotFoundException extends MovieMateBaseException {

    public NotFoundException(String message) {
        super(message, "NOT_FOUND_ERROR");
    }
}
