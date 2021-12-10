package project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApplicationNotFoundException extends RuntimeException {
    public ApplicationNotFoundException() {
    }

    public ApplicationNotFoundException(String message) {
        super(message);
    }

    public ApplicationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationNotFoundException(Throwable cause) {
        super(cause);
    }

    public ApplicationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
