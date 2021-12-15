package project.exception;

public class DisciplineNotFoundException extends RuntimeException {
    public DisciplineNotFoundException() {
    }

    public DisciplineNotFoundException(String message) {
        super(message);
    }

    public DisciplineNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisciplineNotFoundException(Throwable cause) {
        super(cause);
    }

    public DisciplineNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
